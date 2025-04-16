package ir.ha.goodfeeling.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import ir.ha.goodfeeling.data.remote.remote_response.weather.WeatherRemoteResponse
import ir.ha.goodfeeling.data.repository.weather.WeatherRepository
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<HomeScreenVM>()
    val context = LocalContext.current

    GoodFeelingTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                item { Widgets() }
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

    val errorMessage = MutableSharedFlow<String>()

    val weatherLoading = MutableSharedFlow<Boolean>()
    val weatherData = mutableStateOf<WeatherRemoteResponse?>(null)

    val newsLoading = MutableSharedFlow<Boolean>()


    init {
        getCurrentWeather("Tehran")
    }

    fun getCurrentWeather(cityName: String) {
        viewModelScope.launch {
            weatherRepository.getCurrentWeather(cityName).collectLatest { result ->
                when(result){
                    is ResponseState.Loading -> {
                        weatherLoading.emit(true)
                    }
                    is ResponseState.Success -> {
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