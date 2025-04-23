package ir.hasanazimi.avand.data.models.local_entities.other

import androidx.compose.ui.graphics.Color
import ir.hasanazimi.avand.data.models.enums.CurrencyType

data class CurrencyPriceEntity(
    val currencyName: String,
    val currencyFlagId: Int,
    val currencyPrice: String,
    val currencyChangePercent: String,
    val currencyChangePercentColor: Color,
    val currencyUnitType: CurrencyType = CurrencyType.Toman
)
