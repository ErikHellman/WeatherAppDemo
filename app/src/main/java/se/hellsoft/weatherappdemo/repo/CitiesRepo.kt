package se.hellsoft.weatherappdemo.repo

import androidx.annotation.WorkerThread

data class City(val id: Long, val name: String, val country: String)

class CitiesRepo {
    private var cities: List<City> = emptyList()

    @WorkerThread
    fun loadCities(data: String) {
        cities = data
            .split('\n')
            .map {
                val (id, name, country) = it.split((':'))
                City(id.toLong(), name, country)
            }
    }

    fun city(id: Long) = cities.find { it.id == id }

    fun searchCity(query: String): List<City> {
        return cities.filter { it.name.lowercase().startsWith(query.lowercase()) }
    }
}