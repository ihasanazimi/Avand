package ir.hasanazimi.avand.presentation.screens.currency_prices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.currencies.CurrenciesRemoteResponse
import ir.hasanazimi.avand.use_cases.CurrenciesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyPricesScreenVM @Inject constructor(
    private val currenciesUseCase: CurrenciesUseCase
) : ViewModel() {

    private val _currencies = MutableSharedFlow<ResponseState<CurrenciesRemoteResponse>>()
    val currencies = _currencies.asSharedFlow()

    fun getCurrencies(){
        viewModelScope.launch {
            currenciesUseCase.getCurrencies().collect {
                _currencies.emit(it)
            }
        }
    }


}