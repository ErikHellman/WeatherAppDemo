package se.hellsoft.weatherappdemo.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.hellsoft.weatherappdemo.R
import se.hellsoft.weatherappdemo.repo.CityWeather
import se.hellsoft.weatherappdemo.ui.theme.Typography
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun CityCard(
    modifier: Modifier = Modifier,
    cityWeather: CityWeather,
    expanded: Boolean
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .shadow(4.dp)
            .animateContentSize()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
            ) {
                val weatherDescription = cityWeather.description
                val icon = WeatherIcons[cityWeather.icon] ?: R.drawable.weather01d

                Image(
                    modifier = Modifier.size(72.dp),
                    painter = painterResource(id = icon),
                    contentDescription = weatherDescription
                )

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                ) {

                    Text(
                        "${cityWeather.name} ${cityWeather.temperature}°",
                        style = Typography.titleLarge
                    )
                    Text(text = weatherDescription)
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.UnfoldLess else Icons.Default.UnfoldMore,
                    contentDescription = stringResource(id = if (expanded) R.string.description_unfold_less else R.string.description_unfold_more)
                )
            }

            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    Text("Pressure: ${cityWeather.pressure}")
                }

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    Text("Wind: ${cityWeather.windSpeed} m/s ${cardinalDirection(cityWeather.windDirection)}")
                }
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    Text("Clouds: ${cityWeather.clouds}%")
                }

                if (cityWeather.rain > 0f) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    ) {
                        Text("Rain (3h): ${cityWeather.rain} mm")
                    }
                }

                if (cityWeather.snow > 0f) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    ) {
                        Text("Snow (3g): ${cityWeather.snow} mm")
                    }
                }
            }


            val timestamp = LocalDateTime.ofEpochSecond(cityWeather.updated, 0, ZoneOffset.UTC)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp, 8.dp, 8.dp)
            ) {
                Text(
                    text = "Updated: $timestamp"
                )
            }
        }
    }
}

@Suppress("MagicNumber")
@Composable
fun cardinalDirection(degree: Int): String {
    val res = when (degree % 360) {
        in 349..Int.MAX_VALUE -> R.string.direction_n
        in Int.MIN_VALUE..11 -> R.string.direction_n
        in 11..34 -> R.string.direction_nne
        in 34..56 -> R.string.direction_ne
        in 56..79 -> R.string.direction_ene
        in 79..101 -> R.string.direction_e
        in 101..124 -> R.string.direction_ese
        in 124..146 -> R.string.direction_se
        in 146..169 -> R.string.direction_sse
        in 169..191 -> R.string.direction_s
        in 191..214 -> R.string.direction_ssw
        in 214..236 -> R.string.direction_sw
        in 236..259 -> R.string.direction_wsw
        in 259..281 -> R.string.direction_e
        in 281..304 -> R.string.direction_wnw
        in 304..326 -> R.string.direction_nw
        in 326..349 -> R.string.direction_nnw
        else -> R.string.direction_n
    }
    return stringResource(id = res)
}


@Preview
@Composable
fun PreviewCityCard() {
    CityCard(
        cityWeather = CityWeather(
            1,
            "Stockholm",
            "Molnigt",
            "01d",
            10f,
            1000f,
            15f,
            10,
            50,
            0f,
            0f,
            Instant.now().epochSecond
        ), expanded = false
    )
}