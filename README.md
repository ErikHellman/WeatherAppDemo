# Weather App

This project is a weather app that reads the current weather of selected cities from OpenWeatherMap.

The basic architecture is as follows.

The UI is built using Jetpack Compose. 
A thing ViewModel interacts with the a WeatherRepo
The WeatherRepo provides functions for writing weather data to a Room database and fetching 
the latest weather data from OpenWeatherMap. 
A Worker is used to schedule recurring syncs (1/h) to OpenWeatherMap and store the data in the 
Room database.
