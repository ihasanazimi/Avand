package ir.ha.goodfeeling.data.repository.weather

import ir.ha.goodfeeling.data.ResponseState
import ir.ha.goodfeeling.data.remote.remote_response.weather.WeatherRemoteResponse
import ir.ha.goodfeeling.data.remote.webServices.WeatherApiWebServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getCurrentWeather(cityName : String) : Flow<ResponseState<WeatherRemoteResponse>>

}


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiWebServices: WeatherApiWebServices
) : WeatherRepository {

    override suspend fun getCurrentWeather(cityName: String): Flow<ResponseState<WeatherRemoteResponse>>  = flow{
        emit(ResponseState.Loading)
            try {
                val result = weatherApiWebServices.getCurrentWeather(cityName)
                if (result.isSuccessful){
                    result.body()?.let {
                        emit(ResponseState.Success(it))
                    }
                }
            }catch (e : IOException){
                emit(ResponseState.Error(e))
            }
        }
}