package com.example.data.repository

import com.example.core.models.Weather
import com.example.data.Api.WeatherApi
import com.example.domain.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherByCity(city: String): Weather {
        val response = api.getWeatherByCity(city = city, apiKey = "YOUR_API_KEY")
        return Weather(
            cityName = response.name,
            temperature = response.main.temp,
            description = response.weather.firstOrNull()?.description ?: ""
        )
    }
}

