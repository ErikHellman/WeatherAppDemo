@file:OptIn(ExperimentalMaterial3Api::class)

package se.hellsoft.weatherappdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import se.hellsoft.weatherappdemo.ui.CityWeatherScreen
import se.hellsoft.weatherappdemo.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CityWeatherScreen(viewModel)
                }
            }
        }
    }
}
