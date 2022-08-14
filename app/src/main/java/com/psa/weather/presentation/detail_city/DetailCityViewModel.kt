package com.psa.weather.presentation.detail_city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psa.weather.domain.repository.CityRepository
import com.psa.weather.presentation.add_city.WeatherUiState
import com.psa.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCityViewModel @Inject constructor(private val repository: CityRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun getCityWeatherInfo(cityName: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            when (val result = repository.getCityWeatherInfo(cityName, false)) {
                is Resource.Success -> {
                    _uiState.value = WeatherUiState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _uiState.value = WeatherUiState.Error(result.message!!)
                }
            }
        }
    }

}



