package ir.hasanazimi.avand.common.date_time

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

/*
 * *
 *  * Created by Hasan Azimi on 1/8/22, 4:25 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 1/8/22, 4:25 PM
 *
 */


object DateUtils {

    fun formatTimeAgo(inputDateString: String): String {
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val inputDate = dateFormat.parse(inputDateString)
        val now = Calendar.getInstance()
        val inputCalendar = Calendar.getInstance().apply { time = inputDate }

        val diffInMillis = now.timeInMillis - inputCalendar.timeInMillis
        val seconds = diffInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> when (years) {
                1L -> "یک سال پیش"
                2L -> "دو سال پیش"
                else -> "$years سال پیش"
            }
            months > 0 -> when (months) {
                1L -> "یک ماه پیش"
                2L -> "دو ماه پیش"
                else -> "$months ماه پیش"
            }
            weeks > 0 -> when (weeks) {
                1L -> "یک هفته پیش"
                2L -> "دو هفته پیش"
                else -> "$weeks هفته پیش"
            }
            days > 0 -> when (days) {
                1L -> "دیروز"
                2L -> "دو روز پیش"
                else -> "$days روز پیش"
            }
            hours > 0 -> when (hours) {
                1L -> "یک ساعت پیش"
                2L -> "دو ساعت پیش"
                else -> "$hours ساعت پیش"
            }
            minutes > 0 -> when (minutes) {
                1L -> "یک دقیقه پیش"
                2L -> "دو دقیقه پیش"
                else -> "$minutes دقیقه پیش"
            }
            seconds > 10 -> "$seconds ثانیه پیش"
            else -> "لحظاتی پیش"
        }
    }

    /** Sample */
    /*fun main() {
        val testDate1 = "Tue, 22 Apr 2025 12:01:19 GMT" // فرض کنید امروز 24 Apr 2025 باشد
        val testDate2 = "Tue, 23 Apr 2025 14:30:00 GMT" // دیروز
        val testDate3 = "Tue, 23 Apr 2025 15:45:00 GMT" // چند ساعت پیش

        println(formatTimeAgo(testDate1)) // خروجی: "دو روز پیش"
        println(formatTimeAgo(testDate2)) // خروجی: "دیروز"
        println(formatTimeAgo(testDate3)) // خروجی: "x ساعت پیش" (بستگی به زمان فعلی دارد)
    }*/


    fun getGregorianMonthNameInPersian(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "ژانویه"
            2 -> "فوریه"
            3 -> "مارس"
            4 -> "آوریل"
            5 -> "مه"
            6 -> "ژوئن"
            7 -> "ژوئیه"
            8 -> "اوت"
            9 -> "سپتامبر"
            10 -> "اکتبر"
            11 -> "نوامبر"
            12 -> "دسامبر"
            else -> throw IllegalArgumentException("شماره ماه باید بین ۱ تا ۱۲ باشد")
        }
    }

}



// مدل داده برای تاریخ امروز
data class TodayDate(
    val shamsiDate: String, // YYYY/MM/DD
    val gregorianDate: String, // YYYY-MM-DD
    val hijriDate: String // YYYY-MM-DD
)

// کلاس کمکی برای دریافت و تبدیل تاریخ
object DateUtils2 {

    // دریافت تاریخ امروز در سه فرمت
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayDate(): TodayDate {
        // دریافت تاریخ میلادی امروز
        val today = LocalDate.now()
        val gregorianDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE) // YYYY-MM-DD

        // تبدیل به شمسی
        val shamsiDate = convertToShamsi(today)

        // تبدیل به قمری (تقریبی)
        val hijriDate = convertToHijri(today)

        return TodayDate(
            shamsiDate = shamsiDate,
            gregorianDate = gregorianDate,
            hijriDate = hijriDate
        )
    }

    // تبدیل تاریخ میلادی به شمسی (الگوریتم ساده)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToShamsi(date: LocalDate): String {
        val gYear = date.year
        val gMonth = date.monthValue
        val gDay = date.dayOfMonth

        // الگوریتم تقریبی تبدیل
        val jd = gregorianToJulianDay(gYear, gMonth, gDay)
        val shamsi = julianDayToShamsi(jd)

        return String.format("%04d/%02d/%02d", shamsi[0], shamsi[1], shamsi[2])
    }

    // تبدیل تاریخ میلادی به قمری (تقریبی)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToHijri(date: LocalDate): String {
        val gYear = date.year
        val gMonth = date.monthValue
        val gDay = date.dayOfMonth

        // الگوریتم تقریبی
        val jd = gregorianToJulianDay(gYear, gMonth, gDay)
        val hijri = julianDayToHijri(jd)

        return String.format("%04d-%02d-%02d", hijri[0], hijri[1], hijri[2])
    }

    // تبدیل تاریخ میلادی به روز ژولیانی
    private fun gregorianToJulianDay(year: Int, month: Int, day: Int): Double {
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        return day + ((153 * m + 2) / 5) + 365 * y + y / 4 - y / 100 + y / 400 - 32045.0
    }

    // تبدیل روز ژولیانی به شمسی (تقریبی)
    private fun julianDayToShamsi(jd: Double): IntArray {
        val jdn = jd + 0.5
        val de = jdn - 1948320.5 // مبدا تقویم شمسی
        val cycle = (de / 1029983).toInt()
        val cyear = de - cycle * 1029983
        val ycycle = if (cyear < 366) {
            (cyear / 365).toInt()
        } else {
            ((cyear - 1) / 365).toInt()
        }
        val year = 1395 + cycle * 2820 + ycycle
        val doy = (de - (year - 1395) * 365.2422).toInt()
        val month = if (doy <= 186) (doy / 31) + 1 else ((doy - 6) / 30) + 1
        val day = if (doy <= 186) doy % 31 else (doy - 6) % 30

        return intArrayOf(year, month, day + 1)
    }

    // تبدیل روز ژولیانی به قمری (تقریبی)
    private fun julianDayToHijri(jd: Double): IntArray {
        val jdn = jd + 0.5
        val de = jdn - 1948440.5 // مبدا تقویم قمری
        val year = ((de / 354.367) + 0.5).toInt()
        val doy = (de - (year * 354.367)).toInt()
        val month = (doy / 29.53).toInt() + 1
        val day = (doy % 29.53).toInt() + 1

        return intArrayOf(year + 1350, month, day)
    }
}

