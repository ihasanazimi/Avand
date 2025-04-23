package ir.hasanazimi.avand.data.repository

import android.util.Log
import com.google.gson.Gson
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.data.entities.toWeatherEntity
import ir.hasanazimi.avand.data.web_services.weather.WeatherWebServices
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

interface WeatherRepository {


    /**
     * Query parameter based on which data is sent back. It could be following:
     *
     * Latitude and Longitude (Decimal degree) e.g: q=48.8567,2.3508
     * city name e.g.: q=Paris
     * US zip e.g.: q=10001
     * UK postcode e.g: q=SW1
     * Canada postal code e.g: q=G2J
     * metar:<metar code> e.g: q=metar:EGLL
     * iata:<3 digit airport code> e.g: q=iata:DXB
     * auto:ip IP lookup e.g: q=auto:ip
     * IP address (IPv4 and IPv6 supported) e.g: q=100.0.0.1
     * By ID returned from Search API. e.g: q=id:2801268
     * bulk New
     * */

    suspend fun getCurrentWeather(q : String) : Flow<ResponseState<WeatherEntity>>

}


class WeatherRepositoryImpl @Inject constructor(
    private val weatherWebServices: WeatherWebServices,
    private val dataStoreManager: DataStoreManager
) : WeatherRepository {

    val TAG = "WeatherRepositoryImpl"

    override suspend fun getCurrentWeather(q: String): Flow<ResponseState<WeatherEntity>>  = flow{
        emit(ResponseState.Loading)
            try {
                val result = weatherWebServices.getCurrentWeather(q)
                if (result.isSuccessful){
                    result.body()?.let {
                        val weatherEntity = it.toWeatherEntity()
                        dataStoreManager.saveWeatherData(Gson().toJson(weatherEntity))
                        emit(ResponseState.Success(weatherEntity)).also {
                            Log.i(TAG, "getCurrentWeather: ${Gson().toJson(weatherEntity)} ")
                        }
                    }
                }else{
                    emit(ResponseState.Error(Exception(result.message())))
                }
            }catch (e : IOException){
                emit(ResponseState.Error(e))
            }
        }
}