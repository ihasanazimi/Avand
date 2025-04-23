package ir.hasanazimi.avand.data.models.enums


sealed class CurrencyType(val unitType : String) {
    object Toman : CurrencyType(" تومان ")
    object Rial : CurrencyType(" ریال ")
    object Dollar : CurrencyType(" $ ")
}