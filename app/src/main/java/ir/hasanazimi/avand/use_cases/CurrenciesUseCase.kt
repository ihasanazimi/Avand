package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.currencies.CurrenciesRemoteResponse
import ir.hasanazimi.avand.data.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CurrenciesUseCase {

    suspend fun getCurrencies() : Flow<ResponseState<CurrenciesRemoteResponse>>

}





class CurrenciesUseCaseImpl @Inject constructor(
    private val currenciesRepository  :  CurrenciesRepository
) : CurrenciesUseCase{

    val TAG = this::class.simpleName

    override suspend fun getCurrencies(): Flow<ResponseState<CurrenciesRemoteResponse>> {
        return currenciesRepository.getCurrencies()
    }
}