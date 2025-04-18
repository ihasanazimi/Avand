package ir.ha.goodfeeling.data.models.enums

import ir.ha.goodfeeling.R

sealed class WeatherCondition(val code: Int, val iconResId: Int) {


    // normal weathers
    object Sunny : WeatherCondition(1000, R.drawable.sunny) // آفتابی
    object PartlyCloudy : WeatherCondition(1003, R.drawable.partly_cloudy) // نیمه‌ابری
    object Cloudy : WeatherCondition(1006, R.drawable.cloudy) // ابری
    object Overcast : WeatherCondition(1009, R.drawable.cloudy) // کاملاً ابری
    object Mist : WeatherCondition(1030, R.drawable.mist) // مه
    object PatchyRain : WeatherCondition(1063, R.drawable.patchy_rain) // احتمال بارش پراکنده
    object PatchySnow : WeatherCondition(1066, R.drawable.patchy_snow) // احتمال بارش برف پراکنده
    object Thunder : WeatherCondition(1087, R.drawable.thunder) // احتمال رعد و برق
    object Fog : WeatherCondition(1135, R.drawable.mist) // مه‌گرفتگی
    object FreezingFog : WeatherCondition(1147, R.drawable.mist) // مه یخ‌زده
    object LightRain : WeatherCondition(1183, R.drawable.light_rain) // باران سبک
    object ModerateRain : WeatherCondition(1189, R.drawable.light_rain) // باران متوسط
    object HeavyRain : WeatherCondition(1195, R.drawable.heavy_rain) // باران شدید
    object LightSnow : WeatherCondition(1213, R.drawable.patchy_snow) // برف سبک
    object ModerateSnow : WeatherCondition(1219, R.drawable.patchy_snow) // برف متوسط
    object HeavySnow : WeatherCondition(1225, R.drawable.patchy_snow) // برف سنگین
    object Unknown : WeatherCondition(-1, R.drawable.unknown_weather) // ناشناخته / پیش‌فرض


    // night weathers
    object NightSunny : WeatherCondition(1000, R.drawable.moon) // مهتابی
    object NightPartlyCloudy : WeatherCondition(1003, R.drawable.night_cloudy) // نیمه‌ابری
    object NightCloudy : WeatherCondition(1006, R.drawable.night_cloudy) // نیمه‌ابری
    object NightOvercast : WeatherCondition(1009, R.drawable.night_cloudy) // نیمه‌ابری


    companion object {

        fun fromCode(code: Int, isDay: Boolean = false): WeatherCondition {
            return when (code) {
                1000 -> if (isDay) Sunny else NightSunny
                1003 -> if (isDay) PartlyCloudy else NightPartlyCloudy
                1006 -> if (isDay) Cloudy else NightCloudy
                1009 -> if (isDay) Overcast else NightOvercast
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


        fun getSentences(degree: Int): String {
            return when (degree) {
                in -30..-10 -> listOf(
                    "سوپ داغ یادت نره 🍲",
                    "دستکش و شال گردن واجبه 🧣🧤",
                    "یخ زدیم که! بیرون نرو ❄️",
                    "هوا انقد سرده حتی یخ هم لرزید 😨"
                ).random()

                in -9..0 -> listOf(
                    "لباس گرم فراموش نشه 🧥",
                    "کافه و قهوه می‌چسبه ☕️",
                    "بیرون بری سرما می‌خوری 🤧",
                    "نوشیدنی گرم بهترین دوسته الان 🍵"
                ).random()

                in 1..5 -> listOf(
                    "یه کلاه گرم بزنی عالیه 🧢",
                    "آبمیوه نه! هنوز زوده 🍊❌",
                    "اگه می‌ری بیرون، ژاکت فراموش نشه 🧥",
                    "یه آش رشته بزن صفا کن 🍲"
                ).random()

                in 6..10 -> listOf(
                    "هوا دل‌چسبه، ولی بادگیر بزنی بد نیست 🍃🧥",
                    "کتونی بپوش و برو پیاده‌روی 👟",
                    "کاکائو داغ می‌چسبه الان ☕️🍫",
                    "لباس سبک ولی باهات باشه 👕🧥"
                ).random()

                in 11..17 -> listOf(
                    "برو بیرون از هوا لذت ببر ☀️",
                    "پیک‌نیک بهترین گزینه‌ست 🧺",
                    "یه فالوده می‌چسبه 😋",
                    "لباس راحت بپوش و از هوای بهاری لذت ببر 🌸"
                ).random()

                in 18..25 -> listOf(
                    "هوا توپِ توپ 😎",
                    "یه بستنی حتما بزن 🍦",
                    "یه تی‌شرت کافیه 👕",
                    "قدم بزن و آهنگ گوش بده 🎧"
                ).random()

                in 26..32 -> listOf(
                    "آب یادت نره! خیلی مهمه 💧",
                    "لباس روشن بپوش ☀️",
                    "یه هندونه بزن خنک شی 🍉",
                    "سایه پیدا کن، کولر نیست اینجا 😅"
                ).random()

                in 33..40 -> listOf(
                    "بستنی که نه، یخ لازم داری 🧊",
                    "آب یادت نره، گرمازدگی نزنی 💦",
                    "صندل و لباس نخی بهترین گزینه‌ان 👕🩴",
                    "دست از نوشیدنی سرد برندار 🍹"
                ).random()

                in 41..50 -> listOf(
                    "بیرون نری سنگ می‌شی 😵‍💫",
                    "هوا می‌سوزه، کلاه و عینک واجب 🧢🕶",
                    "یه نوشیدنی خیلی خنک بزن 🍧",
                    "سایه و آبجون نجات‌دهنده‌تن 🏖💦"
                ).random()

                in 51..60 -> listOf(
                    "کسی جرأت داره بیاد بیرون؟ 🫠",
                    "خورشیدم گفته بسه دیگه ☀️❌",
                    "فقط یخچال می‌تونه نجاتت بده 🧊🧊",
                    "بستنی هم ذوب میشه تو این هوا 🍦🔥"
                ).random()

                in 61..100 -> listOf(
                    "جهنم؟ نه اینجا زمین ماست 😰",
                    "یخ در بهشت هم بهت رحم نمی‌کنه 🥵",
                    "دستتو بیرون ببری کباب میشه 🔥🍗",
                    "آب، سایه، خنک‌کننده — سه‌گانه بقا 💧☂️🧊"
                ).random()

                else -> listOf(
                    "دمای عجیب‌غریبیه 🤖",
                    "این دما واقعیه؟! 🤯",
                    "الان باید نگران باشیم یا بخندیم؟ 😂",
                    "چی زدی دما شده $degree؟ 😆"
                ).random()
            }
        }
    }


}
