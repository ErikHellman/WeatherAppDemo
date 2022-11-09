package se.hellsoft.weatherappdemo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "type") val type: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "country") val country: String?,
    @Json(name = "sunrise") val sunrise: Int?,
    @Json(name = "sunset") val sunset: Int?
)