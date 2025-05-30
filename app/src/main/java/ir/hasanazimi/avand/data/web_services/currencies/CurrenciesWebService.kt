package ir.hasanazimi.avand.data.web_services.currencies

import ir.hasanazimi.avand.data.entities.remote.currencies.CurrenciesRemoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesWebService {

    @GET("rates")
    suspend fun getCurrenciesPrices(
        @Query("api_key") apiKey : String = "hLM4s9pu5VIHpxTdCHk8Kz8VIF2DRXFCWvomE5kSlwpD2goJLrYW8iK7MGhLsZAX",
        @Query("from") from : String = "IRR"
    ) : Response<CurrenciesRemoteResponse>

}