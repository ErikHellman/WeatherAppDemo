package se.hellsoft.weatherappdemo.repo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityWeather: CityWeather)

    @Delete
    suspend fun delete(cityWeather: CityWeather)

    @Query("DELETE FROM cityweather")
    suspend fun deleteAll()

    @Query("SELECT * FROM cityweather WHERE id = :id")
    fun getForCity(id: Long): Flow<CityWeather>

    @Query("SELECT * FROM cityweather ORDER BY name")
    fun getAll(): Flow<List<CityWeather>>
}

@Database(entities = [CityWeather::class], version = 1)
abstract class CityWeatherDatabase : RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
}