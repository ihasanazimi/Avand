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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.goodfeeling.MainActivity
import ir.ha.goodfeeling.common.extensions.isLocationEnabled
import ir.ha.goodfeeling.common.extensions.showToast
import ir.ha.goodfeeling.common.extensions.turnOnGPS
import ir.ha.goodfeeling.common.more.LocationHelper
import ir.ha.goodfeeling.common.security_and_permissions.askPermission
import ir.ha.goodfeeling.common.security_and_permissions.isPermissionGranted
import ir.ha.goodfeeling.data.ResponseState
import ir.ha.goodfeeling.data.models.local_entities.weather.WeatherEntity
import ir.ha.goodfeeling.data.repository.weather.WeatherRepository
import ir.ha.goodfeeling.db.DataStoreManager
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(activity: MainActivity, navController: NavHostController) {

    val TAG = "HomeScreen"
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenVM>()
    val coroutineScope = rememberCoroutineScope()

    var weatherResponseState by remember { mutableStateOf<ResponseState<WeatherEntity>?>(viewModel.weatherResponse.value) }


    SideEffect {

        viewModel.getCurrentWeatherFromLocal()

        coroutineScope.launch {
            viewModel.errorMessage.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

  /*      coroutineScope.launch {
            viewModel.weatherLoading.collect { it ->
                weatherLoading = it
            }
        }*/

        coroutineScope.launch {
            viewModel.weatherResponse.collect { it ->
                weatherResponseState = it
            }
        }


        coroutineScope.launch {
            activity.locationAccessFinePermissionsResult.collect { result ->

                Log.i(TAG, "HomeScreen: permission result is $result ")

                if (result) {
                    if (activity.isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                        getLastLocation(activity, viewModel)
                    } else {
                        activity.askPermission(
                            permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
                            requestCode = 1001,
                            onPermissionAlreadyGranted = {
                                getLastLocation(activity, viewModel)
                            },
                            onShowRationale = {
                                Toast.makeText(
                                    context,
                                    "برای دریافت موقعیت ممکانی شما نیاز به مجوز داریم",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onRequest = {
                                getLastLocation(activity, viewModel)
                            }
                        )
                    }
                } else {
                    Toast.makeText(
                        context,
                        "اجازه دسترسی به سرویس مکان توسط شما صادر نشده است!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                item {
                    Widgets(
                        weatherData = weatherResponseState,
                        onGetData = {
                            coroutineScope.launch {
                                activity.locationAccessFinePermissionsResult.emit(true)
                            }
                        }
                    )
                }
                item {
                    NewsScreen(navController) {
                        Toast.makeText(context, "more news clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

private fun getLastLocation(
    activity: MainActivity,
    viewModel: HomeScreenVM
) {

    if (isLocationEnabled(activity).not()) {
        turnOnGPS(activity)
        showToast(activity,"لطفا سرویس مکان را روشن کنید")
    } else {

        LocationHelper(activity).getLastLocation(
            onSuccess = {
                val latLng = it.latitude.toString() + "," + it.longitude.toString()
                viewModel.getCurrentWeatherFromRemote(latLng).also {
                    Log.d(
                        "TAG",
                        "LocationHelper(context).getLastLocation - onSuccess by $latLng "
                    )
                }
            },
            onFailure = {
                Log.e(
                    "TAG",
                    "LocationHelper(context).getLastLocation - onFailure by ${it.message} "
                )
                Toast.makeText(
                    activity,
                    "خطا در دریافت موقعیت مکانی ، مجدد تلاش کنید...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val TAG = "HomeScreenVM"

    val errorMessage = MutableSharedFlow<String>()

    var weatherResponse = MutableStateFlow<ResponseState<WeatherEntity>?>(null)
    val newsResponse = MutableStateFlow<ResponseState<List<String>>?>(null)

    fun getCurrentWeatherFromRemote(q: String) {
        Log.i(TAG, "getCurrentWeatherFromRemote called")
        viewModelScope.launch {
            weatherRepository.getCurrentWeather(q).collectLatest { result ->
                Log.i(TAG, "getCurrentWeatherFromRemote: $result ")
                weatherResponse.emit(result)
            }
        }
    }

    fun getCurrentWeatherFromLocal(q: String = "35.761008, 51.404626") {
        Log.i(TAG, "getCurrentWeatherFromLocal called")
        viewModelScope.launch {
            dataStoreManager.weatherDataFlow.collect { cash ->
                /** IF cache was empty then get data from remote */
                if (cash == null) {
                    getCurrentWeatherFromRemote(q)
                } else {
                    try {
                        Log.i(TAG, "getCurrentWeatherFromLocal: local weather is : $cash")
                        val w = Gson().fromJson<WeatherEntity>(
                            cash,
                            WeatherEntity::class.java
                        )
                        weatherResponse.emit(ResponseState.Success(w))
                    } catch (e: IOException) {
                        getCurrentWeatherFromRemote(q)
                        Log.i(TAG, "getCurrentWeatherFromLocal: error is ${e.message}")
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
        HomeScreen(activity = MainActivity(), navController = navController)
    }
}