package ir.ha.goodfeeling.data.entities

import androidx.compose.ui.graphics.Color

data class CurrencyPriceEntity(
    val currencyName: String,
    val currencyFlagId: Int,
    val currencyPrice: String,
    val currencyChangePercent: String,
    val currencyChangePercentColor: Color,
    val currencyUnitType: String = " تومان "
)
