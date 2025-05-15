package ir.hasanazimi.avand.presentation.screens.home

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.date_time.CalendarManager
import ir.hasanazimi.avand.common.date_time.PersianCalendar1
import ir.hasanazimi.avand.common.date_time.RoozhDateConverter
import ir.hasanazimi.avand.common.date_time.getEvents
import ir.hasanazimi.avand.common.extensions.isLocationEnabled
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.turnOnGPS
import ir.hasanazimi.avand.common.more.LocationHelper
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.other.CityEntity
import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.use_cases.WeatherUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val dataStoreManager: DataStoreManager,
    private val context: Context
) : ViewModel() {

    val TAG = "HomeScreenVM"

    val errorMessage = MutableSharedFlow<String>()
    var weatherResponse = MutableStateFlow<ResponseState<WeatherEntity>?>(null)
    var calendarResponse = MutableStateFlow<List<EventOfDayEntity>?>(null)
    var tempOfCalendar = MutableStateFlow<Pair<String, Triple<Int, Int, Int>>?>(null)

    init {
        prepareDates()
        getEvents(context)
        getCurrentWeatherFromLocal()
    }

    fun getCurrentWeatherFromRemote(cityNameOrLatLng: String) {
        Log.i(TAG, "getCurrentWeatherFromRemote called")
        viewModelScope.launch {
            weatherUseCase.getCurrentWeather(cityNameOrLatLng).collectLatest { result ->
                Log.i(TAG, "getCurrentWeatherFromRemote: $result ")
                weatherResponse.emit(result)
            }
        }
    }

    fun getCurrentWeatherFromLocal(q: String = "Tehran") {
        Log.i(TAG, "getCurrentWeatherFromLocal called")
        viewModelScope.launch {
            dataStoreManager.automaticWeatherDataFlow.collectLatest { cash ->
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


    fun prepareWeatherData(activity: MainActivity){
        Log.i(TAG, "prepareWeatherData: ")
        viewModelScope.launch {
            val manual = true
           if (manual){
               getDefaultCity().collect { city ->
                   if (city != null){
                       getCurrentWeatherFromRemote(city.location)
                   }else{
                       activity.locationAccessFinePermissionsResult()
                   }
               }
           }else{
               activity.locationAccessFinePermissionsResult()
           }
        }
    }

    private fun getDefaultCity(): Flow<CityEntity?> = flow {
        dataStoreManager.defaultCity.collect { cityJson ->
            try {
                Log.i(TAG, "getDefaultCity: ")
                val type = object : TypeToken<CityEntity>() {}.type
                val city = Gson().fromJson<CityEntity>(cityJson, type)
                emit(city)
                Log.i(TAG, "getDefaultCity: $city")
            } catch (e: IOException) {
                emit(null)
                Log.d(TAG, "getDefaultCity: ${e.message}")
            }
        }
    }


    private fun getEvents(context: Context){
        viewModelScope.launch {
            val calendarManager = CalendarManager(context)
            calendarManager.loadCalendarData("taghvim.json")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val obj = calendarManager.getDayInfo(PersianCalendar1.shamsiDate())
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



    fun prepareDates(){
        viewModelScope.launch {
            val dateArray = PersianCalendar1.shamsiDate().split("/")
            val persianDate = "${dateArray[2].toInt()} " + PersianCalendar1().strMonth + " ${dateArray[0].toInt()}"
            val roozhDate = RoozhDateConverter()
            roozhDate.persianToGregorian(dateArray[0].toInt(), dateArray[1].toInt(), dateArray[2].toInt())
            val y = roozhDate.year
            val m = roozhDate.month
            val d = roozhDate.day
            tempOfCalendar.emit(Pair(persianDate, Triple(d,m,y)))
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }


}