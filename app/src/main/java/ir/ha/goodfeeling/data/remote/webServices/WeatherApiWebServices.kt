package ir.ha.goodfeeling.data.remote.webServices

import ir.ha.goodfeeling.data.remote.remote_response.weather.WeatherRemoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiWebServices {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") cityName : String,
        @Query("aqi") aqi : String = "yes",
        @Query("key") key : String = "59683c52643e4c96ab780645251604"
    ) : Response<WeatherRemoteResponse>

}