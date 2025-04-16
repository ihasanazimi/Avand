package ir.ha.goodfeeling.data.models.entities

import androidx.compose.ui.graphics.Color
import ir.ha.goodfeeling.data.models.other.CurrencyType

data class CurrencyPriceEntity(
    val currencyName: String,
    val currencyFlagId: Int,
    val currencyPrice: String,
    val currencyChangePercent: String,
    val currencyChangePercentColor: Color,
    val currencyUnitType: CurrencyType = CurrencyType.Toman
)
