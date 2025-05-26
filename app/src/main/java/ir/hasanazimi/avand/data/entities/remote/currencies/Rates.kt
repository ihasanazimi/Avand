package ir.hasanazimi.avand.data.entities.remote.currencies

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Rates(
    @SerializedName("USD") val usd: Int,      // دلار آمریکا
    @SerializedName("CAD") val cad: Double,   // دلار کانادا
    @SerializedName("GBP") val gbp: Double,   // پوند انگلستان
    @SerializedName("EUR") val eur: Double,   // یورو
    @SerializedName("JPY") val jpy: Double,   // ین ژاپن
    @SerializedName("CHF") val chf: Double,   // فرانک سوئیس
    @SerializedName("AUD") val aud: Double,   // دلار استرالیا
    @SerializedName("CNY") val cny: Double,   // یوان چین
    @SerializedName("INR") val inr: Double,   // روپیه هند
    @SerializedName("HKD") val hkd: Double,   // دلار هنگ‌کنگ
    @SerializedName("KRW") val krw: Double,   // وون کره جنوبی
    @SerializedName("SGD") val sgd: Double,   // دلار سنگاپور
    @SerializedName("SEK") val sek: Double,   // کرون سوئد
    @SerializedName("NOK") val nok: Double,   // کرون نروژ
    @SerializedName("MXN") val mxn: Double,   // پزو مکزیک
    @SerializedName("ZAR") val zar: Double,   // راند آفریقای جنوبی
    @SerializedName("TRY") val theTry: Double,   // لیر ترکیه
    @SerializedName("RUB") val rub: Double,   // روبل روسیه
    @SerializedName("NZD") val nzd: Double,   // دلار نیوزیلند
    @SerializedName("TWD") val twd: Double,   // دلار تایوان
    @SerializedName("THB") val thb: Double,   // بات تایلند
    @SerializedName("MYR") val myr: Double,   // رینگیت مالزی
    @SerializedName("IDR") val idr: Double,   // روپیه اندونزی
    @SerializedName("PHP") val php: Double,   // پزو فیلیپین
    @SerializedName("SAR") val sar: Double,   // ریال عربستان
    @SerializedName("AED") val aed: Double,   // درهم امارات
    @SerializedName("KWD") val kwd: Double,   // دینار کویت
    @SerializedName("QAR") val qar: Double,   // ریال قطر
    @SerializedName("BHD") val bhd: Double,   // دینار بحرین
    @SerializedName("JOD") val jod: Double,   // دینار اردن
    @SerializedName("ILS") val ils: Double    // شِکِل اسرائیل
)