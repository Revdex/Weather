package com.psa.weather.presentation.detail_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.psa.weather.R
import com.psa.weather.databinding.FragmentDetailCityBinding
import com.psa.weather.presentation.add_city.WeatherUiState
import com.psa.weather.utils.d
import com.psa.weather.utils.e
import com.psa.weather.utils.getDrawableIdByName
import com.psa.weather.utils.i
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailCityFragment : Fragment() {
    private val viewModel by viewModels<DetailCityViewModel>()
    private var _binding: FragmentDetailCityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("cityId")?.let { cityId ->
            viewModel.getCityWeatherInfo(cityId.toString())
        }
        weatherStateObserver()
    }

    private fun weatherStateObserver() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { uiState ->
                    when (uiState) {
                        is WeatherUiState.Success -> {
                            binding.loader.isVisible = false
                            i("Success", uiState.city)
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