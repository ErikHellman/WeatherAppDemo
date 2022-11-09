package se.hellsoft.weatherappdemo.repo

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import se.hellsoft.weatherappdemo.R
import se.hellsoft.weatherappdemo.cities
import se.hellsoft.weatherappdemo.dataStore
import java.util.concurrent.TimeUnit

@HiltWorker
class CityWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepo: WeatherRepo,
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "CityWeatherWorker"
        const val CHANNEL_ID = "CityWeatherNotificationChannel"
        const val NOTIFICATION_ID = 1234

        fun scheduleDelayed(context: Context) {
            val workManager = WorkManager.getInstance(context)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val workRequest = OneTimeWorkRequestBuilder<CityWeatherWorker>()
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.HOURS)
                .build()
            workManager.enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, workRequest)
        }

        fun scheduleExpedited(context: Context) {
            val workManager = WorkManager.getInstance(context)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val builder = OneTimeWorkRequestBuilder<CityWeatherWorker>()
                .setConstraints(constraints)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)

            val workRequest = builder.build()
            workManager.enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }

    override suspend fun doWork(): Result {
        val preferences = applicationContext.dataStore.data.firstOrNull()
        preferences?.cities?.forEach {
            Log.d(TAG, "doWork: sync for city $it")
            try {
                weatherRepo.syncCurrentWeatherForCity(it)
            } catch (e: Exception) {
                Log.e(TAG, "doWork: exception when syncing cityId $it", e)
            }
        }

        scheduleDelayed(applicationContext)
        return Result.success()
    }

    private fun createNotificationChannel(): NotificationChannelCompat {
        return NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
            .setDescription("For syncing weather data")
            .setName("City Weather Data")
            .build()
    }
    private fun createNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("City Weather")
            .setContentText("Syncing weather information for cities")
            .setSmallIcon(R.drawable.weather03n)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.createNotificationChannel(createNotificationChannel())
        val notification = createNotification()
        return ForegroundInfo(NOTIFICATION_ID, notification)
    }
}