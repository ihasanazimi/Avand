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
    @SerializedName("gregorianDate") val gregorianDate: String? = null,
    @SerializedName("event") val event: String,
    @SerializedName("isOfficialHoliday") val isOfficialHoliday: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String,
    @SerializedName("equivalentShamsiDate") val equivalentShamsiDate: String? = null,
    @SerializedName("equivalentGregorianDate") val equivalentGregorianDate: String? = null,
    @SerializedName("equivalentHijriDate") val equivalentHijriDate: String? = null,
    @SerializedName("note") val note: String? = null
)

data class ShamsiMonth(
    @SerializedName("monthNumber") val monthNumber: Int,
    @SerializedName("monthName") val monthName: String,
    @SerializedName("events") val events: List<Event>
)

data class CalendarData(
    @SerializedName("yearShamsi") val yearShamsi: Int,
    @SerializedName("yearHijri") val yearHijri: Int,
    @SerializedName("yearGregorian") val yearGregorian: Int,
    @SerializedName("monthsShamsi") val monthsShamsi: List<ShamsiMonth>,
    @SerializedName("gregorianEvents") val gregorianEvents: List<Event>
)

data class CalendarInfo(
    val shamsiDate: String,
    val gregorianDate: String,
    val hijriDate: String,
    val isOfficialHoliday: Boolean,
    val events: List<Event>
)

// کلاس اصلی برای مدیریت تقویم
class CalendarManager(private val context: Context) {

    private var calendarData: CalendarData? = null
    private val gson = Gson()

    // خواندن و پارس فایل JSON از assets
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




    fun getTodayHijriDate(): String {
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
        val day = today.get(Calendar.DAY_OF_MONTH)

        val jd = gregorianToJD(year, month, day)
        val hijri = jdToHijri(jd)

        return String.format("%04d-%02d-%02d", hijri[0], hijri[1], hijri[2])
    }

    private fun gregorianToJD(year: Int, month: Int, day: Int): Double {
        val y = if (month > 2) year else year - 1
        val m = if (month > 2) month else month + 12
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) +
                floor(30.6001 * (m + 1)) +
                day + b - 1524.5
    }

    private fun jdToHijri(jd: Double): IntArray {
        val jdAdjusted = floor(jd) + 0.5
        val daysSinceEpoch = jdAdjusted - 1948439.5
        val hijriYear = floor((30 * daysSinceEpoch + 10646) / 10631).toInt()
        val startOfYear = hijriToJD(hijriYear, 1, 1)
        var dayOfYear = (jdAdjusted - startOfYear).toInt()

        var hijriMonth = 1
        while (hijriMonth <= 12 && dayOfYear >= hijriMonthLength(hijriYear, hijriMonth)) {
            dayOfYear -= hijriMonthLength(hijriYear, hijriMonth)
            hijriMonth++
        }

        val hijriDay = dayOfYear + 1
        return intArrayOf(hijriYear, hijriMonth, hijriDay)
    }

    private fun hijriToJD(year: Int, month: Int, day: Int): Double {
        return day +
                ceil(29.5 * (month - 1)) +
                (year - 1) * 354 +
                floor((3 + 11 * year) / 30.0) +
                1948439.5 - 1
    }

    private fun hijriMonthLength(year: Int, month: Int): Int {
        return if ((month % 2 == 1) || (month == 12 && isHijriLeapYear(year))) 30 else 29
    }

    private fun isHijriLeapYear(year: Int): Boolean {
        return ((11 * year + 14) % 30) < 11
    }


    /**
     دریافت اطلاعات روز با ورودی تاریخ‌ها
     * 1404/06/12
     * 2025-09-01
     * 1447-03-10
     */
    fun getDayInfo(shamsiDate: String, gregorianDate: String): CalendarInfo? {
        if (calendarData == null) {
            throw IllegalStateException("Calendar data not loaded. Call loadCalendarData() first.")
        }

        try {
            val hijriDate = getTodayHijriDate()
            // اعتبارسنجی فرمت تاریخ‌ها
            validateDateFormat(shamsiDate, gregorianDate, hijriDate)

            // استخراج اطلاعات
            val events = mutableListOf<Event>()
            var isHoliday = false

            // دریافت مناسبت‌های شمسی
            val shamsiEvents = getShamsiEvents(shamsiDate)
            events.addAll(shamsiEvents)
            isHoliday = isHoliday || shamsiEvents.any { it.isOfficialHoliday }

            // دریافت مناسبت‌های میلادی
            val gregorianEvents = getGregorianEvents(gregorianDate)
            events.addAll(gregorianEvents)
            isHoliday = isHoliday || gregorianEvents.any { it.isOfficialHoliday }

            // دریافت مناسبت‌های قمری (از معادل‌های قمری در رویدادهای شمسی و میلادی)
            val hijriEvents = getHijriEvents(hijriDate, events)
            events.addAll(hijriEvents)
            isHoliday = isHoliday || hijriEvents.any { it.isOfficialHoliday }

            // حذف رویدادهای تکراری (بر اساس نام رویداد)
            val uniqueEvents = events.distinctBy { it.event }

            return CalendarInfo(
                shamsiDate = shamsiDate,
                gregorianDate = gregorianDate,
                hijriDate = hijriDate,
                isOfficialHoliday = isHoliday,
                events = uniqueEvents
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    // اعتبارسنجی فرمت تاریخ‌ها
    private fun validateDateFormat(shamsiDate: String, gregorianDate: String, hijriDate: String) {
        val shamsiRegex = Regex("""\d{4}/\d{2}/\d{2}""")
        val gregorianRegex = Regex("""\d{4}-\d{2}-\d{2}""")
        val hijriRegex = Regex("""\d{4}-\d{2}-\d{2}""")

        if (!shamsiDate.matches(shamsiRegex)) {
            throw IllegalArgumentException("Invalid Shamsi date format. Expected: YYYY/MM/DD")
        }
        if (!gregorianDate.matches(gregorianRegex)) {
            throw IllegalArgumentException("Invalid Gregorian date format. Expected: YYYY-MM-DD")
        }
        if (!hijriDate.matches(hijriRegex)) {
            throw IllegalArgumentException("Invalid Hijri date format. Expected: YYYY-MM-DD")
        }
    }

    // استخراج مناسبت‌های شمسی
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

    // استخراج مناسبت‌های میلادی
    private fun getGregorianEvents(gregorianDate: String): List<Event> {
        return calendarData?.gregorianEvents?.filter { it.gregorianDate == gregorianDate } ?: emptyList()
    }

    // استخراج مناسبت‌های قمری (از معادل‌های قمری در رویدادها)
    private fun getHijriEvents(hijriDate: String, existingEvents: List<Event>): List<Event> {
        val events = mutableListOf<Event>()

        // بررسی رویدادهای شمسی که معادل قمری دارند
        calendarData?.monthsShamsi?.forEach { month ->
            month.events.forEach { event ->
                if (event.equivalentHijriDate == hijriDate && existingEvents.none { it.event == event.event }) {
                    events.add(event)
                }
            }
        }

        // بررسی رویدادهای میلادی که معادل قمری دارند
        calendarData?.gregorianEvents?.forEach { event ->
            if (event.equivalentHijriDate == hijriDate && existingEvents.none { it.event == event.event }) {
                events.add(event)
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