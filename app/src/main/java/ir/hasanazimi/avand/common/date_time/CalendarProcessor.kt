package ir.hasanazimi.avand.common.date_time

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.IOException


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

    /**
     دریافت اطلاعات روز با ورودی تاریخ‌ها
     * 1404/06/12
     * 2025-09-01
     * 1447-03-10
     */
    fun getDayInfo(shamsiDate: String, gregorianDate: String, hijriDate: String): CalendarInfo? {
        if (calendarData == null) {
            throw IllegalStateException("Calendar data not loaded. Call loadCalendarData() first.")
        }

        try {
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