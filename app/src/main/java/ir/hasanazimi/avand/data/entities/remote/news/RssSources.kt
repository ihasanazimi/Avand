package ir.hasanazimi.avand.data.entities.remote.news

import kotlin.reflect.KClass
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.RssFeed as KhabarOnlineRssFeed
import ir.hasanazimi.avand.data.entities.remote.news.zoomit.RssFeed as ZoomitRssFeed

sealed class NewsSources(val name: String, val baseUrl: String, val type: KClass<out Any>) {

    object KHABAR_ONLINE_SIYASI_EGTESAGI : NewsSources(
        name = "خبرآنلاین - سیاسی-اقتصادی",
        baseUrl = "https://www.khabaronline.ir/rss/tp/23",
        type = KhabarOnlineRssFeed::class
    )

    object KHABAR_ONLINE_IT : NewsSources(
        name = "خبرآنلاین - فناوری",
        baseUrl = "https://www.khabaronline.ir/rss/tp/7",
        type = KhabarOnlineRssFeed::class
    )
    object KHABAR_ONLINE : NewsSources(
        name = "خبرآنلاین - آخرینها",
        baseUrl = "https://www.khabaronline.ir/rss",
        type = KhabarOnlineRssFeed::class
    )

    object ZOOMIT : NewsSources(
        name = "زومیت",
        baseUrl = "https://www.zoomit.ir/feed",
        type = ZoomitRssFeed::class
    )
}