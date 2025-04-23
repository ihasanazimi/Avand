package ir.hasanazimi.avand.data.repository.news

/*
interface WeatherRepository {

    suspend fun getCurrentWeather(cityName : String) : Flow<ResponseState<WeatherRemoteResponse>>

}


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiWebServices: WeatherApiWebServices
) : WeatherRepository {

    override suspend fun getCurrentWeather(cityName: String): Flow<ResponseState<WeatherRemoteResponse>>  = flow{
        emit(ResponseState.Loading)
            try {
                val result = weatherApiWebServices.getCurrentWeather()
                if (result.isSuccessful){
                    result.body()?.let {
                        emit(ResponseState.Success(it))
                    }
                }
            }catch (e : IOException){
                emit(ResponseState.Error(e))
            }
        }
}*/
