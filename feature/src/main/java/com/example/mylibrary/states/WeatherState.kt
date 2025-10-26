package com.example.mylibrary.states

import com.example.core.models.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String? = null
)
