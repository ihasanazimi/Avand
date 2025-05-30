package ir.hasanazimi.avand.data.entities.local.currenciees

import androidx.annotation.Keep
import ir.hasanazimi.avand.data.entities.remote.currencies.CurrenciesRemoteResponse


@Keep
data class CurrenciesEntity(
    val unitType: String,
    val ratesList: List<CurrencyEntity>
)

@Keep
data class CurrencyEntity(
    val value: Double = 0.0,
    val enumName: String = "usd"
)

fun CurrenciesRemoteResponse.toCurrenciesEntity(): CurrenciesEntity {
    val ratesList = rates?.let {
        listOfNotNull(
            it.usdEnumKey?.let { key -> CurrencyEntity(it.usd, key) } ?: CurrencyEntity(it.usd, "USD"),
            it.cadEnumKey?.let { key -> CurrencyEntity(it.cad, key) } ?: CurrencyEntity(it.cad, "CAD"),
            it.gbpEnumKey?.let { key -> CurrencyEntity(it.gbp, key) } ?: CurrencyEntity(it.gbp, "GBP"),
            it.eurEnumKey?.let { key -> CurrencyEntity(it.eur, key) } ?: CurrencyEntity(it.eur, "EUR"),
            it.jpyEnumKey?.let { key -> CurrencyEntity(it.jpy, key) } ?: CurrencyEntity(it.jpy, "JPY"),
            it.chfEnumKey?.let { key -> CurrencyEntity(it.chf, key) } ?: CurrencyEntity(it.chf, "CHF"),
            it.audEnumKey?.let { key -> CurrencyEntity(it.aud, key) } ?: CurrencyEntity(it.aud, "AUD"),
            it.cnyEnumKey?.let { key -> CurrencyEntity(it.cny, key) } ?: CurrencyEntity(it.cny, "CNY"),
            it.inrEnumKey?.let { key -> CurrencyEntity(it.inr, key) } ?: CurrencyEntity(it.inr, "INR"),
            it.hkdEnumKey?.let { key -> CurrencyEntity(it.hkd, key) } ?: CurrencyEntity(it.hkd, "HKD"),
            it.krwEnumKey?.let { key -> CurrencyEntity(it.krw, key) } ?: CurrencyEntity(it.krw, "KRW"),
            it.sgdEnumKey?.let { key -> CurrencyEntity(it.sgd, key) } ?: CurrencyEntity(it.sgd, "SGD"),
            it.sekEnumKey?.let { key -> CurrencyEntity(it.sek, key) } ?: CurrencyEntity(it.sek, "SEK"),
            it.nokEnumKey?.let { key -> CurrencyEntity(it.nok, key) } ?: CurrencyEntity(it.nok, "NOK"),
            it.mxnEnumKey?.let { key -> CurrencyEntity(it.mxn, key) } ?: CurrencyEntity(it.mxn, "MXN"),
            it.tryEnumKey?.let { key -> CurrencyEntity(it.theTry, key) } ?: CurrencyEntity(it.theTry, "TRY"),
            it.rubEnumKey?.let { key -> CurrencyEntity(it.rub, key) } ?: CurrencyEntity(it.rub, "RUB"),
            it.nzdEnumKey?.let { key -> CurrencyEntity(it.nzd, key) } ?: CurrencyEntity(it.nzd, "NZD"),
            it.twdEnumKey?.let { key -> CurrencyEntity(it.twd, key) } ?: CurrencyEntity(it.twd, "TWD"),
            it.thbEnumKey?.let { key -> CurrencyEntity(it.thb, key) } ?: CurrencyEntity(it.thb, "THB"),
            it.myrEnumKey?.let { key -> CurrencyEntity(it.myr, key) } ?: CurrencyEntity(it.myr, "MYR"),
            it.idrEnumKey?.let { key -> CurrencyEntity(it.idr, key) } ?: CurrencyEntity(it.idr, "IDR"),
            it.phpEnumKey?.let { key -> CurrencyEntity(it.php, key) } ?: CurrencyEntity(it.php, "PHP"),
            it.sarEnumKey?.let { key -> CurrencyEntity(it.sar, key) } ?: CurrencyEntity(it.sar, "SAR"),
            it.aedEnumKey?.let { key -> CurrencyEntity(it.aed, key) } ?: CurrencyEntity(it.aed, "AED"),
            it.kwdEnumKey?.let { key -> CurrencyEntity(it.kwd, key) } ?: CurrencyEntity(it.kwd, "KWD"),
            it.qarEnumKey?.let { key -> CurrencyEntity(it.qar, key) } ?: CurrencyEntity(it.qar, "QAR"),
            it.bhdEnumKey?.let { key -> CurrencyEntity(it.bhd, key) } ?: CurrencyEntity(it.bhd, "BHD"),
            it.jodEnumKey?.let { key -> CurrencyEntity(it.jod, key) } ?: CurrencyEntity(it.jod, "JOD")
        )
    } ?: emptyList()

    return CurrenciesEntity(
        unitType = base,
        ratesList = ratesList
    )
}