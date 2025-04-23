package ir.hasanazimi.avand.data.entities.local.other

import androidx.compose.ui.graphics.Color
import ir.hasanazimi.avand.data.entities.sealed_enums.CurrencyType

data class CurrencyPriceEntity(
    val currencyName: String,
    val currencyFlagId: Int,
    val currencyPrice: String,
    val currencyChangePercent: String,
    val currencyChangePercentColor: Color,
    val currencyUnitType: CurrencyType = CurrencyType.Toman
)
