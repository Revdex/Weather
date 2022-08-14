package com.psa.weather.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.psa.weather.Constants
import com.psa.weather.domain.model.CityEntity

@Database(entities = [CityEntity::class], version = Constants.DB_VERSION, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {
    abstract val cityDao: CityDao
}