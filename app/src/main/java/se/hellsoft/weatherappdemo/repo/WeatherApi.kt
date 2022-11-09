package se.hellsoft.weatherappdemo.repo

import retrofit2.http.GET
import retrofit2.http.Query
import se.hellsoft.weatherappdemo.models.WeatherData

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun currentWeather(
        @Query("id") cityId: Long,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
    ): WeatherData
}