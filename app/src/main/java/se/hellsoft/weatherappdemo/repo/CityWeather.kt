package se.hellsoft.weatherappdemo.repo

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.hellsoft.weatherappdemo.models.WeatherData
import java.time.Clock

@Entity
data class CityWeather(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val temperature: Float,
    val pressure: Float,
    val windSpeed: Float,
    val windDirection: Int,
    val clouds: Int,
    val rain: Float,
    val snow: Float,
    val updated: Long
)

fun WeatherData.toCityWeather(): CityWeather {
    return CityWeather(
        id!!,
        name!!,
        weather?.first()?.description ?: "",
        weather?.first()?.icon ?: "01d",
        main?.temperature?.toFloat() ?: 0f,
        main?.pressure?.toFloat() ?: 0f,
        wind?.speed?.toFloat() ?: 0f,
        wind?.deg ?: 0,
        clouds?.all ?: 0,
        rain?.threeHours?.toFloat() ?: 0f,
        snow?.threeHours?.toFloat() ?: 0f,
        dt ?: Clock.systemUTC().millis()
    )
}