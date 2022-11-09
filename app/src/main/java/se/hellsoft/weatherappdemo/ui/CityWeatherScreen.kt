package se.hellsoft.weatherappdemo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import se.hellsoft.weatherappdemo.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeatherScreen(viewModel: WeatherViewModel) {
    val citiesWeather = viewModel.allCitiesWeather.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Weather App")
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(citiesWeather.value) {
                    var expanded by remember { mutableStateOf(false) }

                    CityCard(Modifier.clickable { expanded = !expanded }, it, expanded)
                }
            }
        }
    }
}
