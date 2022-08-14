package com.psa.weather.presentation.list_city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psa.weather.domain.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCityViewModel @Inject constructor(private val repository: CityRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.updateFavoritesCityWeatherInfo()
        }
    }

    fun getFavoritesCityWeatherInfo() = repository.getFavoritesCityWeatherInfo()

}







