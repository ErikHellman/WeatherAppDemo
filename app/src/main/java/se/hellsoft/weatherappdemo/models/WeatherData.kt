package se.hellsoft.weatherappdemo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherData(
    @Json(name = "coord") val coord: Coord? = null,
    @Json(name = "weather") val weather: List<Weather>? = null,
    @Json(name = "base") val base: String? = null,
    @Json(name = "main") val main: Main? = null,
    @Json(name = "visibility") val visibility: Int? = null,
    @Json(name = "wind") val wind: Wind? = null,
    @Json(name = "rain") val rain: Rain? = null,
    @Json(name = "snow") val snow: Snow? = null,
    @Json(name = "clouds") val clouds: Clouds? = null,
    @Json(name = "dt") val dt: Long? = null,
    @Json(name = "sys") val sys: Sys? = null,
    @Json(name = "timezone") val timezone: Int? = null,
    @Json(name = "id") val id: Long? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "cod") val cod: Int? = null
)
