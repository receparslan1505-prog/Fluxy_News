package com.example.core.models

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<WeatherDesc>
)

data class Main(val temp: Double)
data class WeatherDesc(val description: String)
