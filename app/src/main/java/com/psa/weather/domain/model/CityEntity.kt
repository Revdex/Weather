package com.psa.weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    var date: String,
    var temperature: String,
    var icon: String,
    var description: String,
    var humidity: String,
    var pressure: String,
    var windSpeed: String,
    var isFavorite: Boolean = false,
)

