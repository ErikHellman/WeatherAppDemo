package se.hellsoft.weatherappdemo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clouds(
    val all: Int?
)