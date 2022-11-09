package se.hellsoft.weatherappdemo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import se.hellsoft.weatherappdemo.repo.CitiesRepo
import se.hellsoft.weatherappdemo.repo.CityWeather
import se.hellsoft.weatherappdemo.repo.WeatherRepo
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val citiesRepo: CitiesRepo,
    private val weatherRepo: WeatherRepo
) : ViewModel() {
    companion object {
        const val TAG = "WeatherViewModel"
    }

    val allCitiesWeather = weatherRepo.currentWeatherForAll()

    fun weatherForCity(cityId: Long): Flow<CityWeather> = weatherRepo.currentWeatherForCity(cityId)
}