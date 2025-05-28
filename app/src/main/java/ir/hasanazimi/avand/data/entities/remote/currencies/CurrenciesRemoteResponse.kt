package ir.hasanazimi.avand.data.entities.remote.currencies

import android.annotation.SuppressLint
import androidx.annotation.Keep
import ir.hasanazimi.avand.data.entities.sealed_enums.CurrencyEntity
import kotlin.reflect.KProperty1


@Keep
data class CurrenciesRemoteResponse(
    val base: String,
    val rates: Rates ?= null
)



@SuppressLint("DefaultLocale")
fun CurrenciesRemoteResponse.toCurrencyEntities(): List<CurrencyEntity> {
    val rates = this.rates ?: return emptyList()
    return rates::class.members
        .filterIsInstance<KProperty1<Any, *>>()
        .filter { it.returnType.classifier == Double::class }
        .map { prop ->
            val price = prop.get(rates)
            val name = prop.name.uppercase()
            val formatedPrice = String.format("%.4f", price as Double)
            CurrencyEntity(value = formatedPrice.toDouble(), enumName = name)
        }
}


