package se.hellsoft.weatherappdemo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    val lon: Double?,
    val lat: Double?
)