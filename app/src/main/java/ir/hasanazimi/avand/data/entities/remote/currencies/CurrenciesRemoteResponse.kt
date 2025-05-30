package ir.hasanazimi.avand.data.entities.remote.currencies

import androidx.annotation.Keep


@Keep
data class CurrenciesRemoteResponse(
    val base: String,
    val rates: Rates ?= null
)



