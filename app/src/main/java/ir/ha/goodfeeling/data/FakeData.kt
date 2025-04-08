package ir.ha.goodfeeling.data

import ir.ha.goodfeeling.data.entities.OccasionsOfTheDayEntity


val occasionsOfTheDayList: ArrayList<OccasionsOfTheDayEntity> = arrayListOf<OccasionsOfTheDayEntity>(
    OccasionsOfTheDayEntity("روز بزرگداشت فردوسی", isHoliday = true),
    OccasionsOfTheDayEntity("روز جوان", isHoliday = false),
    OccasionsOfTheDayEntity("روز مهندس", isHoliday = false),
)