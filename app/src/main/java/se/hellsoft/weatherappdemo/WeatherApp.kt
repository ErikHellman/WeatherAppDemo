package se.hellsoft.weatherappdemo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.work.Configuration
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import se.hellsoft.weatherappdemo.repo.*
import javax.inject.Inject
import javax.inject.Singleton

val defaultCities = listOf(2711533L, 2673723L, 6079660L, 2643741L, 5128581L, 2950158L)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val SELECTED_CITIES_KEY = stringSetPreferencesKey("cities")
val Preferences.cities: List<Long>
    get() {
        return get(SELECTED_CITIES_KEY)?.map { it.toLong() } ?: defaultCities
    }

@HiltAndroidApp
class WeatherApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        CityWeatherWorker.scheduleExpedited(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val moshi = Moshi.Builder().build()
    private val retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.openweathermap.org")
            .build()

    @Provides
    @Singleton
    fun cityWeatherDao(@ApplicationContext context: Context): CityWeatherDao {
        val db = Room.databaseBuilder(context, CityWeatherDatabase::class.java, "city-weather-db")
            .fallbackToDestructiveMigration().build()
        return db.cityWeatherDao()
    }

    @Provides
    @Singleton
    fun citiesRepo() = CitiesRepo()

    @Provides
    @Singleton
    fun weatherRepo(@ApplicationContext appContext: Context, cityWeatherDao: CityWeatherDao) =
        WeatherRepo(
            retrofit.create(WeatherApi::class.java),
            appContext.dataDir,
            cityWeatherDao
        )
}

