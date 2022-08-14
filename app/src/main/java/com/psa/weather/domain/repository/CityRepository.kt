package com.psa.weather.domain.repository

import com.psa.weather.domain.model.CityEntity
import com.psa.weather.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun updateFavoritesCityWeatherInfo()

    suspend fun addCityToFavorite(city: CityEntity)

    fun searchCityByName(name: String): Flow<List<CityEntity>>

    suspend fun getCityWeatherInfo(cityId: String, fetchFromRemote: Boolean): Resource<CityEntity>

    fun getFavoritesCityWeatherInfo(): Flow<List<CityEntity>>

}