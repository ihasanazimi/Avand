package ir.hasanazimi.avand.presentation.screens.setting

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.common.file.AssetHelper
import ir.hasanazimi.avand.data.entities.local.other.CityEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class SettingScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val context: Context
) : ViewModel() {

    val TAG = "SettingScreenVM"

    val userName = MutableStateFlow("")
    val defaultCity = MutableStateFlow<CityEntity?>(null)
    var snapShotCities = mutableStateListOf<CityEntity>()

    fun saveAndNotifyUserName(newName: String) {
        viewModelScope.launch {
            dataStoreManager.saveUserName(newName)
            userName.emit(newName)
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect {
                Log.i(TAG, "getUserName: $it")
                userName.emit(it ?: "کاربر بدون نام")
            }
        }
    }

    private fun readCitiesFromJson() : List<CityEntity>{
        var localCities = emptyList<CityEntity>()
        val jsonText = AssetHelper.readJsonFromAssets(context, "cities.json")
        val typeToken = object : TypeToken<List<CityEntity>>() {}.type
        runCatching {
            localCities = Gson().fromJson<List<CityEntity>>(jsonText, typeToken)
            localCities = localCities.sortedBy { it.cityName }
        }.onFailure {
            Log.i(TAG, "prepareLocalCities: ${it.message}")
        }
        return localCities
    }

    fun prepareCities() : SnapshotStateList<CityEntity>{
        var tempOfCities = readCitiesFromJson()
        val selectedCity = tempOfCities.find { it.cityName == defaultCity.value?.cityName }
        val toc = tempOfCities.map {
            if (it.cityName == selectedCity?.cityName){
                it.copy(selected = true)
            }else{
                it.copy(selected = false)
            }
        }
        snapShotCities.clear()
        snapShotCities.addAll(toc)
        return snapShotCities
    }




    fun saveAndNotifyManualWeatherData(weatherEntity: WeatherEntity) {
        viewModelScope.launch {
            val json = Gson().toJson(weatherEntity)
            dataStoreManager.saveManualWeatherData(json)
        }
    }
    fun getManualWeatherData(){
        viewModelScope.launch {
            dataStoreManager.manualWeatherData.collect {
                // todo
            }
        }
    }
    fun removeManualWeatherData() {
        viewModelScope.launch {
            dataStoreManager.removeManualWeatherData()
        }
    }




    fun saveAndNotifyDefaultCity(cityEntity: CityEntity?){
        viewModelScope.launch {
            if (cityEntity == null) return@launch
            val json = Gson().toJson(cityEntity)
            dataStoreManager.saveDefaultCity(json)
            defaultCity.emit(cityEntity)
        }
    }
    fun getDefaultCity(){
        viewModelScope.launch {
            dataStoreManager.defaultCity.collect { cityJson ->
                try{
                    val type = object : TypeToken<CityEntity>(){}.type
                    val city = Gson().fromJson<CityEntity>(cityJson,type)
                    defaultCity.emit(city)
                    Log.i(TAG, "getDefaultCity: $city")
                }catch (e : IOException){
                    Log.d(TAG, "getDefaultCity: ${e.message}")
                }
            }
        }
    }
    fun removeDefaultCity(){
        viewModelScope.launch {
            dataStoreManager.removeDefaultCity()
            defaultCity.emit(null)
        }
    }





    fun getManualWeather() {
        viewModelScope.launch {
            dataStoreManager.manualWeatherData.collect { location ->

            }
        }
    }




}