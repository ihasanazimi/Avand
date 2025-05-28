package ir.hasanazimi.avand.data.entities.sealed_enums

import ir.hasanazimi.avand.R


sealed class CurrencyType(val unitType : String) {
    object Toman : CurrencyType(" تومان ")
    object Rial : CurrencyType(" ریال ")
    object Dollar : CurrencyType(" $ ")
}


data class CurrencyEntity(
    val value: Double,
    val enumName: String,
)

sealed class CurrencyEnum(
    val code: String,
    val displayNameFa: String,
    val icon: Int,
    var price : Double = 0.0,
    val priority : Int,
    val currencyType: CurrencyType = CurrencyType.Dollar
) {
    object USD : CurrencyEnum("USD", "دلار آمریکا", R.drawable.england, priority = 1)
    object CAD : CurrencyEnum("CAD", "دلار کانادا", R.drawable.england, priority = 2)
    object GBP : CurrencyEnum("GBP", "پوند انگلستان", R.drawable.england, priority = 3)
    object EUR : CurrencyEnum("EUR", "یورو", R.drawable.england, priority = 4)
    object JPY : CurrencyEnum("JPY", "ین ژاپن", R.drawable.england, priority = 5)
    object CHF : CurrencyEnum("CHF", "فرانک سوئیس", R.drawable.england, priority = 6)
    object AUD : CurrencyEnum("AUD", "دلار استرالیا", R.drawable.england, priority = 7)
    object CNY : CurrencyEnum("CNY", "یوان چین", R.drawable.england, priority = 8)
    object INR : CurrencyEnum("INR", "روپیه هند", R.drawable.england, priority = 9)
    object HKD : CurrencyEnum("HKD", "دلار هنگ‌کنگ", R.drawable.england, priority = 10)
    object TRY : CurrencyEnum("TRY", "لیر ترکیه", R.drawable.england, priority = 11)
    object KRW : CurrencyEnum("KRW", "وون کره جنوبی", R.drawable.england, priority = 12)
    object SGD : CurrencyEnum("SGD", "دلار سنگاپور", R.drawable.england, priority = 13)
    object SEK : CurrencyEnum("SEK", "کرون سوئد", R.drawable.england, priority = 14)
    object NOK : CurrencyEnum("NOK", "کرون نروژ", R.drawable.england, priority = 15)
    object MXN : CurrencyEnum("MXN", "پزو مکزیک", R.drawable.england, priority = 16)
    object RUB : CurrencyEnum("RUB", "روبل روسیه", R.drawable.england, priority = 17)
    object NZD : CurrencyEnum("NZD", "دلار نیوزیلند", R.drawable.england, priority = 18)
    object TWD : CurrencyEnum("TWD", "دلار تایوان", R.drawable.england, priority = 19)
    object THB : CurrencyEnum("THB", "بات تایلند", R.drawable.england, priority = 20)
    object MYR : CurrencyEnum("MYR", "رینگیت مالزی", R.drawable.england, priority = 21)
    object IDR : CurrencyEnum("IDR", "روپیه اندونزی", R.drawable.england, priority = 22)
    object PHP : CurrencyEnum("PHP", "پزو فیلیپین", R.drawable.england, priority = 23)
    object SAR : CurrencyEnum("SAR", "ریال عربستان", R.drawable.england, priority = 24)
    object AED : CurrencyEnum("AED", "درهم امارات", R.drawable.england, priority = 25)
    object KWD : CurrencyEnum("KWD", "دینار کویت", R.drawable.england, priority = 26)
    object QAR : CurrencyEnum("QAR", "ریال قطر", R.drawable.england, priority = 27)
    object BHD : CurrencyEnum("BHD", "دینار بحرین", R.drawable.england, priority = 28)
    object JOD : CurrencyEnum("JOD", "دینار اردن", R.drawable.england, priority = 29)

    companion object {
        val all = listOf(
            USD, CAD, GBP, EUR, JPY, CHF, AUD, CNY, INR, HKD,
            KRW, SGD, SEK, NOK, MXN, TRY, RUB, NZD, TWD,
            THB, MYR, IDR, PHP, SAR, AED, KWD, QAR, BHD, JOD
        )

        fun fromCode(code: String): CurrencyEnum? {
            return all.find { it.code == code }
        }

        fun hasInList(code: String): Boolean {
            return all.any { it.code == code }
        }
    }
}
