package ir.hasanazimi.avand.data.entities.sealed_enums


sealed class CurrencyType(val unitType : String) {
    object Toman : CurrencyType(" تومان ")
    object Rial : CurrencyType(" ریال ")
    object Dollar : CurrencyType(" $ ")
}