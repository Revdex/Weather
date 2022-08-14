package com.psa.weather.presentation.list_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.psa.weather.R
import com.psa.weather.databinding.FragmentListCityBinding
import com.psa.weather.domain.model.CityEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListCityFragment : Fragment() {

    private val viewModel by viewModels<ListCityViewModel>()
    private var _binding: FragmentListCityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_add)

        }
        val cityAdapter = CityAdapter(object : CityAdapter.OnItemClickListener {
            override fun onItemClick(city: CityEntity) {
                findNavController().navigate(R.id.action_list_to_detail, bundleOf("cityId" to city.id))
            }
        })
        binding.apply {
            binding.recyclerCities.apply {
                adapter = cityAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        lifecycleScope.launch {
            viewModel.getFavoritesCityWeatherInfo().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { cities ->
                    binding.loader.isVisible = false
                    cityAdapter.submitList(cities)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}