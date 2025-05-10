package ir.hasanazimi.avand.common.date_time

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity
import java.io.IOException
import java.util.Calendar
import kotlin.math.ceil
import kotlin.math.floor


// مدل‌های داده (بدون تغییر)
data class Event(
    @SerializedName("day") val day: Int? = null,
    @SerializedName("event") val event: String,
    @SerializedName("isOfficialHoliday") val isOfficialHoliday: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String,
)

data class ShamsiMonth(
    @SerializedName("monthNumber") val monthNumber: Int,
    @SerializedName("monthName") val monthName: String,
    @SerializedName("events") val events: List<Event>
)

data class CalendarData(
    @SerializedName("monthsShamsi") val monthsShamsi: List<ShamsiMonth>,
)

data class CalendarInfo(
    val shamsiDate: String,
    val isOfficialHoliday: Boolean,
    val events: List<Event>
)

class CalendarManager(private val context: Context) {

    private var calendarData: CalendarData? = null
    private val gson = Gson()

    fun loadCalendarData(fileName: String = ""): Boolean {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            calendarData = gson.fromJson(jsonString, CalendarData::class.java)
            calendarData != null
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    /**
     دریافت اطلاعات روز با ورودی تاریخ‌ها
     * 1404/06/12
     */
    fun getDayInfo(shamsiDate: String): CalendarInfo? {
        if (calendarData == null) {
            throw IllegalStateException("Calendar data not loaded. Call loadCalendarData() first.")
        }
        try {
            val events = mutableListOf<Event>()
            var isHoliday = false
            val shamsiEvents = getShamsiEvents(shamsiDate)
            events.addAll(shamsiEvents)
            isHoliday = isHoliday || shamsiEvents.any { it.isOfficialHoliday }
            val uniqueEvents = events.distinctBy { it.event }
            return CalendarInfo(
                shamsiDate = shamsiDate,
                isOfficialHoliday = isHoliday,
                events = uniqueEvents
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun validateDateFormat(shamsiDate: String, gregorianDate: String, hijriDate: String) {
        val shamsiRegex = Regex("""\d{4}/\d{2}/\d{2}""")

        if (!shamsiDate.matches(shamsiRegex)) {
            throw IllegalArgumentException("Invalid Shamsi date format. Expected: YYYY/MM/DD")
        }
    }

    private fun getShamsiEvents(shamsiDate: String): List<Event> {
        val events = mutableListOf<Event>()
        val (year, month, day) = shamsiDate.split("/").map { it.toInt() }

        calendarData?.monthsShamsi?.forEach { shamsiMonth ->
            if (shamsiMonth.monthNumber == month) {
                shamsiMonth.events.forEach { event ->
                    if (event.day == day) {
                        events.add(event)
                    }
                }
            }
        }
        return events
    }
}


fun CalendarInfo.getEvents() : List<EventOfDayEntity> {
    val tempArr = mutableListOf<EventOfDayEntity>()
    this.events.forEach {
        tempArr.add(EventOfDayEntity(eventTitle = it.event, isHoliday = it.isOfficialHoliday))
    }
    return tempArr
}

fun Event.toEventOfDay() = EventOfDayEntity(eventTitle = this.event, isHoliday = this.isOfficialHoliday)