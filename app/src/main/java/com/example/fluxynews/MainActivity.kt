package com.example.fluxynews

import WeatherScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mylibrary.viewModel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: WeatherViewModel by viewModels()

        initializeApp(viewModel)
    }

    private fun initializeApp(viewModel: WeatherViewModel) {

        setContent {
            val state by viewModel.weatherState.collectAsState()

            WeatherScreen(state = state, onRefresh = {
                viewModel.refreshWeather()
            })
        }
    }
}
