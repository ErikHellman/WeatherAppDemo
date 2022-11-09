package se.hellsoft.weatherappdemo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Double?,
    @Json(name = "deg") val deg: Int?,
    @Json(name = "gust") val gust: Double?
)