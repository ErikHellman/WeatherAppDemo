package se.hellsoft.weatherappdemo.repo

import kotlinx.coroutines.flow.Flow
import se.hellsoft.weatherappdemo.BuildConfig
import java.io.File

class WeatherRepo(
    private val weatherApi: WeatherApi,
    private val dataFolder: File,
    private val cityWeatherDao: CityWeatherDao
) {
    suspend fun syncCurrentWeatherForCity(cityId: Long) {
        val currentWeatherData = weatherApi.currentWeather(cityId, BuildConfig.API_KEY)
        cityWeatherDao.insert(currentWeatherData.toCityWeather())
    }

    fun currentWeatherForCity(cityId: Long): Flow<CityWeather> = cityWeatherDao.getForCity(cityId)

    fun currentWeatherForAll() = cityWeatherDao.getAll()
}
