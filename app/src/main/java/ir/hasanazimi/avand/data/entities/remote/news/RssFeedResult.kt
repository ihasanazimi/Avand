package ir.hasanazimi.avand.data.entities.remote.news

import ir.hasanazimi.avand.data.entities.remote.news.egtesad_online.RssFeed as EgtesadOnlineRssFeed
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.RssFeed as KhabarOnlineRssFeed
import ir.hasanazimi.avand.data.entities.remote.news.zoomit.RssFeed as ZoomitRssFeed

sealed interface RssFeedResult {
    data class EgtesadOnline(val feed: EgtesadOnlineRssFeed) : RssFeedResult
    data class KhabarOnline(val feed: KhabarOnlineRssFeed) : RssFeedResult
    data class Zoomit(val feed: ZoomitRssFeed) : RssFeedResult
}