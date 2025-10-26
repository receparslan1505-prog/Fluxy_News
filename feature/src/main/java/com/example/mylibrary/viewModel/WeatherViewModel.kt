package com.example.mylibrary.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.WeatherRepository
import com.example.mylibrary.states.WeatherState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState

    fun loadWeatherByLocation(context: Context) {
        viewModelScope.launch {
            _weatherState.value = WeatherState(isLoading = true)

            val location = getLocation(context) // helper method
            val city = getCityFromLocation(context, location)

            try {
                val weather = repository.getWeatherByCity(city)
                _weatherState.value = WeatherState(weather = weather)
            } catch (e: Exception) {
                _weatherState.value = WeatherState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun refreshWeather() {
        // Tekrar yükleme için aynı metodu çağırabilirsin
    }


    @SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context): Location {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        return suspendCancellableCoroutine  { cont ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(location)
                    } else {
                        cont.resumeWithException(Exception("Location not found"))
                    }
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e)
                }
        }
    }

    private fun getCityFromLocation(context: Context, location: Location): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return addresses?.firstOrNull()?.locality ?: "Ankara" // fallback
    }
}