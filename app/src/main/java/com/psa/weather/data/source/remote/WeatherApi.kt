package com.psa.weather.data.source.remote

import com.psa.weather.data.source.remote.dto.CityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WEATHER)
    suspend fun getWeatherInfo(
        @Query("APPID") appid: String = APPID,
        @Query("units") untis: String = METRIC,
        @Query("id") cityId: String,
    ): CityDto

    companion object {
        /*Url*/
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val WEATHER = "weather"

        /*Params*/
        const val APPID = "86594815291794b414a29283b2a7d52b"
        const val METRIC = "metric"

    }
}