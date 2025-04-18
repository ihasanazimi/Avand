package ir.ha.goodfeeling.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.goodfeeling.data.ResponseState
import ir.ha.goodfeeling.data.models.remote_response.weather.WeatherRemoteResponse
import ir.ha.goodfeeling.data.repository.weather.WeatherRepository
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenVM>()
    val coroutineScope = rememberCoroutineScope()

    var weatherLoading by remember { mutableStateOf(viewModel.weatherLoading.value) }
    var weatherData by remember { mutableStateOf(viewModel.weatherData.value) }


    SideEffect {

        viewModel.getCurrentWeather(q = "35.561,51.235")

        coroutineScope.launch {
            viewModel.errorMessage.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        coroutineScope.launch {
            viewModel.weatherLoading.collect { it ->
                weatherLoading = it
            }
        }

        coroutineScope.launch {
            viewModel.weatherData.collect { it ->
                weatherData = it
            }
        }
    }

    GoodFeelingTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                item { Widgets(
                    weatherLoading = weatherLoading,
                    weatherData = weatherData,
                    onRefresh = {
                        viewModel.getCurrentWeather(q = "35.561,51.235")
                    }
                ) }
                item {
                    NewsScreen(navController) {
                        Toast.makeText(context, "more news clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val TAG = "HomeScreenVM"

    val errorMessage = MutableSharedFlow<String>()

    val weatherLoading = MutableStateFlow<Boolean>(false)
    val weatherData = MutableStateFlow<WeatherRemoteResponse?>(null)

    val newsLoading = MutableSharedFlow<Boolean>()
    val newsData = MutableSharedFlow<List<String>>()

    fun getCurrentWeather(q: String) {
        viewModelScope.launch {
            weatherRepository.getCurrentWeather(q).collectLatest { result ->
                Log.i(TAG, "getCurrentWeather: $result ")
                when(result){
                    is ResponseState.Loading -> {
                        weatherLoading.emit(true)
                    }
                    is ResponseState.Success -> {
                        delay(500)
                        weatherLoading.emit(false)
                        weatherData.value = result.data
                    }
                    is ResponseState.Error -> {
                        weatherLoading.emit(false)
                        errorMessage.emit("خطای نامشخص")
                    }
                }
            }
        }
    }

    fun getNews() {

    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}