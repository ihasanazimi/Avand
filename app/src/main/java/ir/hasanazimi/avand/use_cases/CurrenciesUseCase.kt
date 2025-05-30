package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.currenciees.CurrencyEntity
import ir.hasanazimi.avand.data.entities.local.currenciees.toCurrenciesEntity
import ir.hasanazimi.avand.data.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CurrenciesUseCase {

    suspend fun getCurrenciesData(): Flow<ResponseState<List<CurrencyEntity>>>

}


class CurrenciesUseCaseImpl @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : CurrenciesUseCase {

    val TAG = this::class.simpleName

    override suspend fun getCurrenciesData(): Flow<ResponseState<List<CurrencyEntity>>> = flow {
        val apiCallRes = currenciesRepository.getCurrencies()
        apiCallRes.collect {
            when (it) {

                is ResponseState.Success -> {
                    val data = it.data?.toCurrenciesEntity()
                    val currenciesList = data?.ratesList ?: emptyList()
                    emit(ResponseState.Success(currenciesList))
                }

                is ResponseState.Loading -> {
                    emit(ResponseState.Loading)
                }

                is ResponseState.Error -> {
                    emit(ResponseState.Error(Exception("خطای نامشخص")))
                }

            }
        }
    }
}