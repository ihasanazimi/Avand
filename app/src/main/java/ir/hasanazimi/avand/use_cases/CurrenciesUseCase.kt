package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.common.extensions.withNotNull
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.sealed_enums.CurrencyEnum
import ir.hasanazimi.avand.data.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface CurrenciesUseCase {

    suspend fun getCurrencies() : Flow<ResponseState<List<CurrencyEnum>>>

}





class CurrenciesUseCaseImpl @Inject constructor(
    private val currenciesRepository  :  CurrenciesRepository
) : CurrenciesUseCase{

    val TAG = this::class.simpleName

    override suspend fun getCurrencies(): Flow<ResponseState<List<CurrencyEnum>>> = flow {
        currenciesRepository.getCurrencies().onEach { result ->
            if (result is ResponseState.Success){
                val newList = arrayListOf<CurrencyEnum?>()
                result.data.withNotNull { nonNullList ->
                    nonNullList.forEach { item ->
                        val obj = CurrencyEnum.fromCode(item.enumName)
                        obj?.price = item.value
                        newList.add(obj)
                    }
                    val fnn = newList.filterNotNull()
                    emit(ResponseState.Success(fnn))
                }
            }
        }
    }
}