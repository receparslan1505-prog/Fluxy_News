package com.example.domain

import com.example.core.models.Weather


interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): Weather

}