import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylibrary.states.WeatherState

@Composable
fun WeatherScreen(state: WeatherState, onRefresh: () -> Unit) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = state.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onRefresh() }) { // <-- lambda bu şekilde olmalı
                    Text("Retry")
                }
            }
        }
        state.weather != null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = state.weather.cityName, fontSize = 24.sp)
                Text(text = "${state.weather.temperature}°C", fontSize = 48.sp)
                Text(text = state.weather.description)
            }
        }
    }
}
