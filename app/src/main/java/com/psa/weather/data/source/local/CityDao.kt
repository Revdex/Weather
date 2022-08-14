package com.psa.weather.data.source.local

import androidx.room.*
import com.psa.weather.domain.model.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cities: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: CityEntity)

    @Update
    suspend fun update(city: CityEntity)

    @Delete
    suspend fun delete(city: CityEntity)

    @Query("DELETE FROM city")
    suspend fun delete(): Int

    @Query("SELECT * FROM city WHERE name LIKE :name || '%'")
    fun getCityName(name: String): Flow<List<CityEntity>>

    @Query("SELECT * FROM city WHERE id = :cityId")
    suspend fun getCityByID(cityId: String): CityEntity

    @Query("SELECT * FROM city")
    suspend fun findAll(): List<CityEntity>

    @Query("SELECT * FROM city ORDER BY id LIMIT 1")
    suspend fun getCity(): CityEntity?

    @Query("SELECT * FROM city WHERE isFavorite")
    suspend fun getFavoritesCity(): List<CityEntity>

    @Query("SELECT * FROM city WHERE isFavorite")
    fun getFavoritesCityAsFlow(): Flow<List<CityEntity>>

}