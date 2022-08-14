package com.psa.weather.presentation.add_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.psa.weather.R
import com.psa.weather.databinding.FragmentAddCityBinding
import com.psa.weather.domain.model.CityEntity
import com.psa.weather.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCityFragment : Fragment() {
    private val viewModel by viewModels<AddCityViewModel>()
    private var _binding: FragmentAddCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArrayAdapter<String>
    private var cities: List<CityEntity> = emptyList()
    private var selectedCity: CityEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWeatherStateObserver()
        setupCityAdapter()
    }

    private fun setupCityAdapter() {
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, emptyList()
        )
        binding.city.setAdapter(adapter)
        binding.city.doAfterTextChanged { text ->
            if (text != null && text.length > 2) {
                wtf("Value $text")
                searchCity(text.toString())
            }
        }
        binding.city.setOnItemClickListener { _, _, i, _ ->
            selectedCity = cities[i]
            v("City $selectedCity")
            viewModel.getCityWeatherInfo(selectedCity?.id.toString())
        }
        binding.add.setOnClickListener {
            selectedCity?.let {
                viewModel.addCityToFavorite(it)
                findNavController().navigateUp()
            }
        }

    }

    private fun searchCity(cityName: String) {
        lifecycleScope.launch {
            viewModel.searchCityByName(name = cityName)
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { cities ->
                    this@AddCityFragment.cities = cities
                    adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line, cities.map { it.name }.distinct()
                    )
                    binding.city.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                }
        }
    }

    private fun setupWeatherStateObserver() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { uiState ->
                    when (uiState) {
                        is WeatherUiState.Success -> {
                            i("Success", uiState.city)
                            selectedCity = uiState.city
                            binding.loader.isVisible = false
                            binding.cityName.text = uiState.city.name
                            binding.date.text = uiState.city.date
                            binding.temperature.text = uiState.city.temperature
                            binding.icon.setImageResource(requireContext().getDrawableIdByName(uiState.city.icon))
                            binding.description.text = uiState.city.description
                            binding.humidity.text = uiState.city.humidity
                            binding.pressure.text = uiState.city.pressure
                            binding.wind.text = uiState.city.windSpeed
                        }
                        is WeatherUiState.Error -> {
                            binding.loader.isVisible = false
                            e("Error", uiState.error)
                        }
                        is WeatherUiState.Loading -> {
                            binding.loader.isVisible = true
                            d("Loading")
                        }
                        is WeatherUiState.Empty -> {
                            binding.loader.isVisible = false
                            d("Empty")
                            binding.cityName.text = " - "
                            binding.date.text = " - "
                            binding.temperature.text = " - °C"
                            binding.icon.setImageResource(R.drawable.ic_50n)
                            binding.description.text = " - "
                            binding.wind.text = " - m/s"
                            binding.humidity.text = " - %"
                            binding.pressure.text = " - hPa"
                        }
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}