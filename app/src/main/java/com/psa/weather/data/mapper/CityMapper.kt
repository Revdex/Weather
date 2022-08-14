package com.psa.weather.data.mapper

import com.psa.weather.data.source.remote.dto.CityDto
import com.psa.weather.domain.model.CityEntity
import com.psa.weather.utils.DateTimeUtils

fun CityDto.toCityInfo(): CityEntity {
    return CityEntity(
        id = id ?: 0,
        name = name ?: "",
        country = sys?.country ?: "",
        longitude = coord?.lon ?: 0.0,
        latitude = coord?.lat ?: 0.0,
        date = DateTimeUtils.getDate(dt?.toLong() ?: 0) ?: "",
        temperature = "${main?.temp?.toInt()}°C",
        icon = weather[0].icon ?: "",
        description = weather[0].description?.replaceFirstChar { it.uppercase() } ?: "",
        humidity = "${main?.humidity ?: ""} %",
        pressure = "${main?.pressure ?: ""}  hPa",
        windSpeed = "${wind?.speed ?: ""} m/s",
    )

}




