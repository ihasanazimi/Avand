package ir.hasanazimi.avand.data.repository

import android.util.Log
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.currencies.CurrenciesRemoteResponse
import ir.hasanazimi.avand.data.web_services.currencies.CurrenciesWebService
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

interface CurrenciesRepository {

    suspend fun getCurrencies() : Flow<ResponseState<CurrenciesRemoteResponse>>

}





class CurrenciesRepositoryImpl @Inject constructor(
    private val currenciesWenService  :  CurrenciesWebService,
    private val dataStoreManager: DataStoreManager
) : CurrenciesRepository{

    val TAG = this::class.simpleName

    override suspend fun getCurrencies(): Flow<ResponseState<CurrenciesRemoteResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = currenciesWenService.getCurrenciesPrices()
            if (response.isSuccessful){
                emit(ResponseState.Success(response.body()))
            }else{
                emit(ResponseState.Error(IOException()))
            }
        }catch (e : IOException){
            Log.e(TAG, "getCurrencies: ${e.message} ", )
        }
    }
}