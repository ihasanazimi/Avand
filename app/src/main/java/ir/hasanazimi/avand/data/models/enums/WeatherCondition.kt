package ir.hasanazimi.avand.data.models.enums

import ir.hasanazimi.avand.R

sealed class WeatherCondition(val code: Int, val iconResId: Int) {


    // normal weathers
    object Sunny : WeatherCondition(1000, R.drawable.sunny) // آفتابی
    object PartlyCloudy : WeatherCondition(1003, R.drawable.partly_cloudy) // نیمه‌ابری
    object Cloudy : WeatherCondition(1006, R.drawable.night_cloudy) // ابری
    object Overcast : WeatherCondition(1009, R.drawable.night_cloudy) // کاملاً ابری
    object Mist : WeatherCondition(1030, R.drawable.foggy) // مه
    object PatchyRain : WeatherCondition(1063, R.drawable.heavy_rain) // احتمال بارش پراکنده
    object PatchySnow : WeatherCondition(1066, R.drawable.patchy_snow) // احتمال بارش برف پراکنده
    object Thunder : WeatherCondition(1087, R.drawable.thunder) // احتمال رعد و برق
    object Fog : WeatherCondition(1135, R.drawable.foggy) // مه‌گرفتگی
    object FreezingFog : WeatherCondition(1147, R.drawable.foggy) // مه یخ‌زده
    object LightRain : WeatherCondition(1183, R.drawable.heavy_rain) // باران سبک
    object ModerateRain : WeatherCondition(1189, R.drawable.heavy_rain) // باران متوسط
    object HeavyRain : WeatherCondition(1195, R.drawable.heavy_rain2) // باران شدید
    object LightSnow : WeatherCondition(1213, R.drawable.patchy_snow) // برف سبک
    object ModerateSnow : WeatherCondition(1219, R.drawable.patchy_snow) // برف متوسط
    object HeavySnow : WeatherCondition(1225, R.drawable.patchy_snow) // برف سنگین
    object Unknown : WeatherCondition(-1, R.drawable.loading) // ناشناخته / پیش‌فرض


    // night weathers
    object NightSunny : WeatherCondition(1000, R.drawable.moon) // مهتابی


    companion object {

        fun fromCode(code: Int, isDay: Boolean = false): WeatherCondition {
            return when (code) {
                1000 -> if (isDay) Sunny else NightSunny
                1003 -> PartlyCloudy
                1006 -> Cloudy
                1009 -> Overcast
                1030 -> Mist
                1063 -> PatchyRain
                1066 -> PatchySnow
                1087 -> Thunder
                1135 -> Fog
                1147 -> FreezingFog
                1183 -> LightRain
                1189 -> ModerateRain
                1195 -> HeavyRain
                1213 -> LightSnow
                1219 -> ModerateSnow
                1225 -> HeavySnow
                else -> Unknown
            }
        }


        fun getSentences(degree: Int, isDay: Boolean = true): String {
            return if (!isDay) {
                when (degree) {
                    in Int.MIN_VALUE..0 -> listOf(
                        "هوا یخ کرده، پتو بکش 😴",
                        "بخاری رو زیاد کن 🧤",
                        "شبا سردتره، حواست باشه ❄️",
                        "لباس گرم یادت نره 🌙"
                    ).random()

                    in 1..10 -> listOf(
                        "هوای خنک شب، عالیه برای خواب 😌",
                        "یه فنجون چای شبونه 🍵",
                        "پتو سبک بنداز روی خودت 🛏",
                        "آرامش شبانه با هوای عالی 😴"
                    ).random()

                    in 11..24 -> listOf(
                        "نه گرمه نه سرده، فقط بخواب 😴",
                        "پنجره رو نیمه باز بذار 🌬",
                        "خواب خوش با هوای لطیف 🌙",
                        "یه پلی‌لیست آروم بذار و بخواب 🎶"
                    ).random()

                    in 25..36 -> listOf(
                        "شبا هم گرمه، پنکه روشن کن 🌀",
                        "کولر روشن؟ حله 😎",
                        "رو ملحفه سبک بخواب 😴",
                        "شب گرم، لباس سبک بپوش 👕"
                    ).random()

                    in 37..Int.MAX_VALUE -> listOf(
                        "خیلی گرمه، آب کنار دستت باشه 💧",
                        "قبل خواب دوش بگیر خنک شی 🚿",
                        "پنجره رو ببند، پشه هم هست 😅",
                        "کولر رو بذار روی تایمر 🌬⏰"
                    ).random()
                    else -> {
                        listOf<String>("نامشخص").random()
                    }
                }
            } else {
                when (degree) {
                    in Int.MIN_VALUE..-15 -> listOf(
                        "یخ زدیم که! بیرون نرو ❄️",
                        "بخاری رو سفت بچسب 😨",
                        "هوا واقعا خطرناکه، مراقب باش ⚠️",
                        "هرچی لباس داری بپوش 🧥🧣🧤"
                    ).random()

                    in -14..0 -> listOf(
                        "سوپ داغ یادت نره 🍲",
                        "دستکش و شال گردن واجبه 🧣🧤",
                        "سرما استخوان‌سوزه 😬",
                        "حواست به گوش‌هات باشه 🧏"
                    ).random()

                    in 1..10 -> listOf(
                        "لباس گرم فراموش نشه 🧥",
                        "کافه و قهوه می‌چسبه ☕️",
                        "بیرون بری سرما می‌خوری 🤧",
                        "نوشیدنی گرم بهترین دوسته الان 🍵"
                    ).random()

                    in 11..17 -> listOf(
                        "هوا خوبه ولی یه سوییشرت بزن 😌",
                        "قدم بزن، لذت ببر 🚶",
                        "نه گرمه نه سرده، عالیه 😎",
                        "توپ‌ترین هوا واسه ورزش 🏃"
                    ).random()

                    in 18..24 -> listOf(
                        "هوا توپِ توپ 😎",
                        "یه بستنی حتما بزن 🍦",
                        "یه تی‌شرت کافیه 👕",
                        "قدم بزن و آهنگ گوش بده 🎧"
                    ).random()

                    in 25..30 -> listOf(
                        "آب یادت نره! خیلی مهمه 💧",
                        "لباس روشن بپوش ☀️",
                        "یه هندونه بزن خنک شی 🍉",
                        "سایه پیدا کن، کولر نیست اینجا 😅"
                    ).random()

                    in 31..36 -> listOf(
                        "بستنی که نه، یخ لازم داری 🧊",
                        "آب یادت نره، گرمازدگی نزنی 💦",
                        "صندل و لباس نخی بهترین گزینه‌ان 👕🩴",
                        "دست از نوشیدنی سرد برندار 🍹"
                    ).random()

                    in 37..45 -> listOf(
                        "بیرون نری سنگ می‌شی 😵‍💫",
                        "هوا می‌سوزه، کلاه و عینک واجب 🧢🕶",
                        "یه نوشیدنی خیلی خنک بزن 🍧",
                        "سایه و آبجون نجات‌دهنده‌تن 🏖💦"
                    ).random()

                    in 46..Int.MAX_VALUE -> listOf(
                        "جهنم؟ نه اینجا زمین ماست 😰",
                        "یخ در بهشت هم بهت رحم نمی‌کنه 🥵",
                        "دستتو بیرون ببری کباب میشه 🔥🍗",
                        "آب، سایه، خنک‌کننده — سه‌گانه بقا 💧☂️🧊"
                    ).random()

                    else -> {
                        listOf<String>("نامشخص").random()
                    }
                }
            }
        }




        fun getUVSentences(uv: Int, isDay: Boolean = false): String {
            return if (!isDay) {
                // حالت شب
                listOf(
                    "شب شده",
                    "اشعه‌ای در کار نیست",
                    "زمان استراحت",
                    "خورشید خوابه"
                ).random()
            } else {
                // حالت روز
                when (uv) {
                    in 0..2 -> listOf(
                        "آروم و امن",
                        "آفتاب ملایم",
                        "نگران نباش"
                    ).random()

                    in 3..5 -> listOf(
                        "عینک بزن",
                        "کلاه یادت نره",
                        "پوستتو حفظ کن"
                    ).random()

                    in 6..7 -> listOf(
                        "ضدآفتاب واجبه",
                        "سایه پیداکن",
                        "زیاد بیرون نرو"
                    ).random()

                    in 8..10 -> listOf(
                        "خطرناک شد",
                        "بیرون نرو",
                        "پوستو می‌سوزونه 🔥"
                    ).random()

                    in 11..Int.MAX_VALUE -> listOf(
                        "فرار کن 🏃‍♂️🔥",
                        "پوست می‌سوزه 😖",
                        "اشعه شدید ☣️"
                    ).random()

                    else -> listOf(
                        "درجه نامعتبر 🤔",
                        "چک کن بازم 🔄"
                    ).random()
                }
            }
        }


    }


}
