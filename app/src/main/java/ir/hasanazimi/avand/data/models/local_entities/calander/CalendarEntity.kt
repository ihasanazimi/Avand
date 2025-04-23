package ir.hasanazimi.avand.data.models.local_entities.calander

import ir.hasanazimi.avand.data.models.local_entities.other.OccasionsOfTheDayEntity


data class CalendarEntity(
    var dayOfWeek: String,
    var globalDate : String ,
    var persianDate : String,
    var occasionsOfTheDayEntities: List<OccasionsOfTheDayEntity>
)
