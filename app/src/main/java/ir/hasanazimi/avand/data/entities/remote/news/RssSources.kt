package ir.hasanazimi.avand.data.entities.remote.news

import kotlin.reflect.KClass
import ir.hasanazimi.avand.data.entities.remote.news.egtesad_online.RssFeed as EgtesadOnlineRssFeed
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.RssFeed as KhabarOnlineRssFeed
import ir.hasanazimi.avand.data.entities.remote.news.zoomit.RssFeed as ZoomitRssFeed

sealed class NewsSources(val name: String, val baseUrl: String, val type: KClass<out Any>) {

    object EGTESAGE_ONLINE : NewsSources(
        name = "اقتصاد آنلاین",
        baseUrl = "https://www.eghtesadonline.com/fa/rss/29/122",
        type = EgtesadOnlineRssFeed::class
    )

    object KHABAR_ONLINE : NewsSources(
        name = "خبرآنلاین",
        baseUrl = "https://www.khabaronline.ir/rss",
        type = KhabarOnlineRssFeed::class
    )

    object ZOOMIT : NewsSources(
        name = "زومیت",
        baseUrl = "https://www.zoomit.ir/feed",
        type = ZoomitRssFeed::class
    )
}