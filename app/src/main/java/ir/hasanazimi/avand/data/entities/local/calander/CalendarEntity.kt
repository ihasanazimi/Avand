package ir.hasanazimi.avand.data.entities.local.calander

import ir.hasanazimi.avand.data.entities.local.other.OccasionsOfTheDayEntity


data class CalendarEntity(
    var dayOfWeek: String,
    var globalDate : String ,
    var persianDate : String,
    var occasionsOfTheDayEntities: List<OccasionsOfTheDayEntity>
)
