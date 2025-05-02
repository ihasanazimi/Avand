package ir.hasanazimi.avand.presentation.screens

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.date_time.CalendarManager
import ir.hasanazimi.avand.common.date_time.DateUtils
import ir.hasanazimi.avand.common.date_time.DateUtils2
import ir.hasanazimi.avand.common.date_time.PersianCalendar1
import ir.hasanazimi.avand.common.date_time.RoozhDateConverter
import ir.hasanazimi.avand.common.date_time.getEvents
import ir.hasanazimi.avand.common.extensions.isLocationEnabled
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.turnOnGPS
import ir.hasanazimi.avand.common.more.LocationHelper
import ir.hasanazimi.avand.common.security_and_permissions.askPermission
import ir.hasanazimi.avand.common.security_and_permissions.isPermissionGranted
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.calander.CalendarEntity
import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.RssFeedResult
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.use_cases.NewsRssUseCase
import ir.hasanazimi.avand.use_cases.WeatherUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(activity: MainActivity, navController: NavHostController) {

    val TAG = "HomeScreen"
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenVM>()
    val coroutineScope = rememberCoroutineScope()


    var newsResponseState = viewModel.newsResponse.collectAsStateWithLifecycle()
    var weatherResponseState = viewModel.weatherResponse.collectAsStateWithLifecycle()
    var errorMessageState = viewModel.errorMessage.collectAsStateWithLifecycle("")


    val dateArray = PersianCalendar1.shamsiDate().split("/")
    val persianDate = "${dateArray[2].toInt()} " + PersianCalendar1().strMonth + " ${dateArray[0].toInt()}"
    val roozhDate = RoozhDateConverter()
    roozhDate.persianToGregorian(dateArray[0].toInt(), dateArray[1].toInt(), dateArray[2].toInt())
    val y = roozhDate.year
    val m = roozhDate.month
    val d = roozhDate.day
    var calendarEntityState by remember {
        mutableStateOf<CalendarEntity>(
            CalendarEntity(
                dayOfWeek = PersianCalendar1().strWeekDay,
                globalDate = "${d.toInt()}  ${DateUtils.getGregorianMonthNameInPersian(m.toInt())}  ${y.toInt()}",
                persianDate = persianDate,
                events = viewModel.calendarResponse.value ?: emptyList()
            )
        )
    }



    LaunchedEffect(Unit) {

        viewModel.getCurrentWeatherFromLocal()
        viewModel.getNewsRss()
        viewModel.getEvents(context)

        if (errorMessageState.value.isNotEmpty()){
            Toast.makeText(context, errorMessageState.value, Toast.LENGTH_SHORT).show()
        }

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
                        weatherData = weatherResponseState.value,
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
                        activity = activity,
                        navController = navController,
                        newsState = newsResponseState.value
                    ) {
                        viewModel.getNewsRss()
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
        showToast(activity, "لطفا سرویس مکان را روشن کنید")
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
                viewModel.weatherResponse.value =
                    ResponseState.Error(Exception("خطا در دریافت موقعیت مکانی"))
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
    var calendarResponse = MutableStateFlow<List<EventOfDayEntity>?>(null)


    private val _newsResponse = MutableStateFlow<ResponseState<List<RssFeedResult?>>>(ResponseState.Loading)
    val newsResponse = _newsResponse.asStateFlow()

    fun getCurrentWeatherFromRemote(q: String) {
        Log.i(TAG, "getCurrentWeatherFromRemote called")
        viewModelScope.launch {
            weatherUseCase.getCurrentWeather(q).collectLatest { result ->
                Log.i(TAG, "getCurrentWeatherFromRemote: $result ")
                weatherResponse.emit(result)
            }
        }
    }

    fun getCurrentWeatherFromLocal(q: String = "Tehran") {
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


    fun getNewsRss() {
        viewModelScope.launch {

            val feeds = listOf(
                NewsSources.ZOOMIT,
                NewsSources.KHABAR_ONLINE,
                NewsSources.KHABAR_ONLINE_IT,
                NewsSources.KHABAR_ONLINE_SIYASI_EGTESAGI,
            )

            newsRssUseCase.getAllNews(feeds).collect { result ->
                when (result) {
                    is ResponseState.Success -> {
                        _newsResponse.emit(ResponseState.Success(result.data))
                    }
                    is ResponseState.Error -> {
                        result.exception?.let { _newsResponse.emit(ResponseState.Error(it)) }
                    }
                    is ResponseState.Loading -> {
                        _newsResponse.emit(ResponseState.Loading)
                    }
                }
            }
        }
    }



    fun getEvents(context: Context){
        viewModelScope.launch {
            val calendarManager = CalendarManager(context)
            calendarManager.loadCalendarData("taghvim.json")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val obj = calendarManager.getDayInfo(
                    shamsiDate = PersianCalendar1.shamsiDate(),
                    gregorianDate = DateUtils2.getTodayDate().gregorianDate,
                    hijriDate = "1406-10-01"
                )
                calendarResponse.emit(obj?.getEvents())
                Log.i(TAG, "getEvents: $obj")
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AvandTheme {
        HomeScreen(
            activity = MainActivity(),
            navController = rememberNavController()
        )
    }
}