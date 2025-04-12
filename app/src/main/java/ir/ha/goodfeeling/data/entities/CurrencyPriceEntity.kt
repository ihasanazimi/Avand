package ir.ha.goodfeeling.data.entities

import androidx.compose.ui.graphics.Color
import ir.ha.goodfeeling.data.CurrencyType

data class CurrencyPriceEntity(
    val currencyName: String,
    val currencyFlagId: Int,
    val currencyPrice: String,
    val currencyChangePercent: String,
    val currencyChangePercentColor: Color,
    val currencyUnitType: CurrencyType = CurrencyType.Toman
)
