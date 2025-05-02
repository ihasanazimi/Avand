package ir.hasanazimi.avand.presentation.screens.home

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.date_time.CalendarManager
import ir.hasanazimi.avand.common.date_time.DateUtils2
import ir.hasanazimi.avand.common.date_time.PersianCalendar1
import ir.hasanazimi.avand.common.date_time.getEvents
import ir.hasanazimi.avand.common.extensions.isLocationEnabled
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.turnOnGPS
import ir.hasanazimi.avand.common.more.LocationHelper
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.use_cases.NewsRssUseCase
import ir.hasanazimi.avand.use_cases.WeatherUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


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






    fun getEvents(context: Context){
        viewModelScope.launch {
            val calendarManager = CalendarManager(context)
            calendarManager.loadCalendarData("taghvim.json")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val obj = calendarManager.getDayInfo(
                    shamsiDate = PersianCalendar1.shamsiDate(),
                    gregorianDate = DateUtils2.getTodayDate().gregorianDate,
                )
                calendarResponse.emit(obj?.getEvents())
                Log.i(TAG, "getEvents: $obj")
            }
        }
    }





    fun getLastLocation(
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



}