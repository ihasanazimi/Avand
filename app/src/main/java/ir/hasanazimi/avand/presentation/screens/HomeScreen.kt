package ir.hasanazimi.avand.presentation.screens

import android.Manifest
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.extensions.isLocationEnabled
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.turnOnGPS
import ir.hasanazimi.avand.common.more.LocationHelper
import ir.hasanazimi.avand.common.security_and_permissions.askPermission
import ir.hasanazimi.avand.common.security_and_permissions.isPermissionGranted
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.calander.CalendarEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.data.entities.remote.news.Item
import ir.hasanazimi.avand.data.fakeOccasionsOfTheDayList
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.use_cases.NewsRssUseCase
import ir.hasanazimi.avand.use_cases.WeatherUseCase
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
    var calendarEntityState by remember { mutableStateOf<CalendarEntity>(
        CalendarEntity(
            dayOfWeek = "سه شنبه",
            globalDate = "15 آوریل 2025",
            persianDate = "26 فروردین 1404",
            fakeOccasionsOfTheDayList
        )
    ) }

    var newsResponseState by remember { mutableStateOf<ResponseState<List<Item>>?>(viewModel.newsResponse.value) }


    SideEffect {

        viewModel.getCurrentWeatherFromLocal()
        viewModel.getNewsRss()

        coroutineScope.launch {
            viewModel.errorMessage.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        coroutineScope.launch {
            viewModel.weatherResponse.collect { it ->
                weatherResponseState = it
            }
        }


        coroutineScope.launch {
            viewModel.newsResponse.collect{ newsItems ->
                newsResponseState = newsItems
            }
        }


        coroutineScope.launch {
            activity.locationAccessFinePermissionsResult.collect { result ->

                Log.i(TAG, "HomeScreen: permission result is $result ")

                if (result) {
                    if (activity.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        getLastLocation(activity, viewModel)
                    } else {
                        activity.askPermission(
                            permission = Manifest.permission.ACCESS_FINE_LOCATION,
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

    AvandTheme {
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
                        activity = activity,
                        weatherData = weatherResponseState,
                        calendarData = calendarEntityState,
                        onGetData = {
                            coroutineScope.launch {
                                activity.locationAccessFinePermissionsResult.emit(true)
                            }
                        }
                    )
                }
                item {
                    NewsScreen(
                        navController = navController,
                        newsData = newsResponseState
                    ) {
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
        viewModel.weatherResponse.value = ResponseState.Loading
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
                    "خطا در دریافت موقعیت مکانی",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.weatherResponse.value = ResponseState.Error(Exception("خطا در دریافت موقعیت مکانی"))
            }
        )
    }
}


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val newsRssUseCase: NewsRssUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val TAG = "HomeScreenVM"

    val errorMessage = MutableSharedFlow<String>()

    var weatherResponse = MutableStateFlow<ResponseState<WeatherEntity>?>(null)
    var newsResponse = MutableStateFlow<ResponseState<List<Item>>?>(null)

    fun getCurrentWeatherFromRemote(q: String) {
        Log.i(TAG, "getCurrentWeatherFromRemote called")
        viewModelScope.launch {
            weatherUseCase.getCurrentWeather(q).collectLatest { result ->
                Log.i(TAG, "getCurrentWeatherFromRemote: $result ")
                weatherResponse.emit(result)
            }
        }
    }

    fun getCurrentWeatherFromLocal(q: String = "35.6892,51.3890") {
        Log.i(TAG, "getCurrentWeatherFromLocal called")
        viewModelScope.launch {
            dataStoreManager.automaticWeatherDataFlow.collect { cash ->
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



    fun getNewsRss(){
        viewModelScope.launch {
            newsRssUseCase.getNews().collect {
                Log.i(TAG, "getNewsRss: ${it.data}")
                when(it){
                    is ResponseState.Success -> {
                        newsResponse.emit(ResponseState.Success(it.data?.toMutableStateList()))
                    }
                    is ResponseState.Error -> {
                        newsResponse.emit(ResponseState.Error(IOException(it.exception)))
                    }
                    is ResponseState.Loading -> {
                        newsResponse.emit(ResponseState.Loading)
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AvandTheme {
        HomeScreen(activity = MainActivity(), navController = rememberNavController())
    }
}