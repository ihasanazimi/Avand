package ir.hasanazimi.avand.data.entities.local.calander

import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity


data class CalendarEntity(
    var dayOfWeek: String,
    var globalDate : String ,
    var persianDate : String,
    var events: List<EventOfDayEntity>
)
