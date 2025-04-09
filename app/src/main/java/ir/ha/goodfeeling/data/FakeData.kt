package ir.ha.goodfeeling.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.data.entities.CityEntity
import ir.ha.goodfeeling.data.entities.CurrencyPriceEntity
import ir.ha.goodfeeling.data.entities.OccasionsOfTheDayEntity
import ir.ha.goodfeeling.ui.theme.GreenColor
import ir.ha.goodfeeling.ui.theme.RedColor


val occasionsOfTheDayList: ArrayList<OccasionsOfTheDayEntity> = arrayListOf<OccasionsOfTheDayEntity>(
    OccasionsOfTheDayEntity("روز بزرگداشت فردوسی", isHoliday = true),
    OccasionsOfTheDayEntity("روز جوان", isHoliday = false),
    OccasionsOfTheDayEntity("روز مهندس", isHoliday = false),
)



val bitPriceList: ArrayList<CurrencyPriceEntity> = arrayListOf<CurrencyPriceEntity>(
    CurrencyPriceEntity(
        currencyName = "بیت کوین",
        currencyFlagId = R.drawable.bitcoin,
        currencyPrice = "103,250",
        currencyChangePercent = "3.1" + " % ",
        currencyChangePercentColor = GreenColor,
        currencyUnitType = " $ "
    ),
    CurrencyPriceEntity(
        currencyName = "اتریوم",
        currencyFlagId = R.drawable.ethereum,
        currencyPrice = "103,250",
        currencyChangePercent = "1.8" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = " $ "
    ),
    CurrencyPriceEntity(
        currencyName = "تتر",
        currencyFlagId = R.drawable.tether,
        currencyPrice = "103,250",
        currencyChangePercent = "2.5" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = "تومان"
    )
)


val goldPriceList: ArrayList<CurrencyPriceEntity> = arrayListOf<CurrencyPriceEntity>(
    CurrencyPriceEntity(
        currencyName = "طلا 18 عیار",
        currencyFlagId = R.drawable.gold,
        currencyPrice = "8,100,370",
        currencyChangePercent = "5.1" + " % ",
        currencyChangePercentColor = GreenColor,
        currencyUnitType = " تومان "
    ),
    CurrencyPriceEntity(
        currencyName = "سکه امامی",
        currencyFlagId = R.drawable.golden_coin,
        currencyPrice = "98,103,250",
        currencyChangePercent = "1.8" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = " تومان "
    )
)

val currencyPriceList: ArrayList<CurrencyPriceEntity> = arrayListOf<CurrencyPriceEntity>(
    CurrencyPriceEntity(
        currencyName = "دلار",
        currencyFlagId = R.drawable.us,
        currencyPrice = "103,250",
        currencyChangePercent = "3.1" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = "تومان"
    ),
    CurrencyPriceEntity(
        currencyName = "یورو",
        currencyFlagId = R.drawable.euro,
        currencyPrice = "103,250",
        currencyChangePercent = "1.8" + " % ",
        currencyChangePercentColor = GreenColor,
        currencyUnitType = "تومان"
    ),
    CurrencyPriceEntity(
        currencyName = "پوند",
        currencyFlagId = R.drawable.england,
        currencyPrice = "103,250",
        currencyChangePercent = "2.5" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = "تومان"
    )
)



fun cities() : SnapshotStateList<CityEntity> {
    val mutableStateListOf = mutableStateListOf<CityEntity>()
    val _cities = arrayListOf<CityEntity>(
        CityEntity(cityName = "تهران", location = "", selected = false),
        CityEntity(cityName = "کاشان", location = "", selected = false),
        CityEntity(cityName = "اصفهان", location = "", selected = false),
        CityEntity(cityName = "اهواز", location = "", selected = false),
        CityEntity(cityName = "مازندران", location = "", selected = false),
        CityEntity(cityName = "زنجان", location = "", selected = false),
        CityEntity(cityName = "تبریز", location = "", selected = false),
        CityEntity(cityName = "گلستان", location = "", selected = false),
        CityEntity(cityName = "بروجرد", location = "", selected = false),
        CityEntity(cityName = "یاسوج", location = "", selected = false),
        CityEntity(cityName = "لرستان", location = "", selected = false),
        CityEntity(cityName = "مشهد", location = "", selected = false),
        CityEntity(cityName = "اردبیل", location = "", selected = false),
    )
    val temp = _cities.sortedBy { it.selected }
    temp.forEach {
        mutableStateListOf.add(it)
    }
    return mutableStateListOf
}
