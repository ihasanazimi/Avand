package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.data.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherUseCase {

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

    suspend fun getCurrentWeather(q: String): Flow<ResponseState<WeatherEntity>>

}



class WeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherUseCase{
    override suspend fun getCurrentWeather(q: String): Flow<ResponseState<WeatherEntity>> {
        return weatherRepository.getCurrentWeather(q)
    }
}