package com.psa.weather.data.source.local

import com.psa.weather.domain.model.CityEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityLocalDataSource @Inject constructor(
    private val cityDao: CityDao
) {
    suspend fun insert(cities: List<CityEntity>) {
        cityDao.insert(cities)
    }

    suspend fun update(city: CityEntity) {
        cityDao.update(city)
    }

    suspend fun getFavoritesCity(): List<CityEntity> {
        return cityDao.getFavoritesCity()
    }

    suspend fun getCityByID(cityId: String): CityEntity {
        return cityDao.getCityByID(cityId)
    }

    fun getCityByName(name: String): Flow<List<CityEntity>> {
        return cityDao.getCityName(name)
    }

    fun getFavoritesCityAsFlow(): Flow<List<CityEntity>> {
        return cityDao.getFavoritesCityAsFlow()
    }

}