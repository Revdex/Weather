package com.psa.weather.data.repository

import com.psa.weather.data.mapper.toCityInfo
import com.psa.weather.data.source.local.CityLocalDataSource
import com.psa.weather.data.source.remote.CityRemoteDataSource
import com.psa.weather.domain.model.CityEntity
import com.psa.weather.domain.repository.CityRepository
import com.psa.weather.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepositoryImpl @Inject constructor(
    private val localDataSource: CityLocalDataSource,
    private val remoteDataSource: CityRemoteDataSource,
) : CityRepository {

    override suspend fun updateFavoritesCityWeatherInfo() {
        localDataSource.getFavoritesCity()
            .forEach { city ->
                val cityWeatherInfo = remoteDataSource.getCityWeatherInfo(city.id.toString())
                localDataSource.update(cityWeatherInfo.toCityInfo().copy(isFavorite = true))
            }
    }

    override fun getFavoritesCityWeatherInfo(): Flow<List<CityEntity>> {
        return localDataSource.getFavoritesCityAsFlow()
    }

    override suspend fun addCityToFavorite(city: CityEntity) {
        city.isFavorite = true
        localDataSource.update(city)
    }

    override fun searchCityByName(name: String): Flow<List<CityEntity>> {
        return localDataSource.getCityByName(name)
    }

    override suspend fun getCityWeatherInfo(cityId: String, fetchFromRemote: Boolean) =
        try {
            val city = if (fetchFromRemote) {
                remoteDataSource.getCityWeatherInfo(cityId).toCityInfo()
            } else {
                localDataSource.getCityByID(cityId)
            }
            Resource.Success(city)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }


}