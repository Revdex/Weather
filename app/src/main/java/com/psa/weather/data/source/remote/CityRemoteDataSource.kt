package com.psa.weather.data.source.remote

import com.psa.weather.data.source.remote.dto.CityDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRemoteDataSource @Inject constructor(
    private val serviceEndpoint: WeatherApi
) {

    suspend fun getCityWeatherInfo(cityId: String) = serviceEndpoint.getWeatherInfo(cityId = cityId)

    suspend fun getCitiesWeatherInfo(cityIds: List<String>): List<CityDto> {
        val cities = ArrayList<CityDto>()
        cityIds.forEach { cityId ->
            val city = getCityWeatherInfo(cityId)
            cities.add(city)
        }
        return cities
    }


}