package ir.hasanazimi.avand.data.entities.remote.news

import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.RssFeed as KhabarOnlineRssFeed
import ir.hasanazimi.avand.data.entities.remote.news.zoomit.RssFeed as ZoomitRssFeed

sealed interface RssFeedResult {
    val isEmpty: Boolean

    data class KhabarOnline(val feed: KhabarOnlineRssFeed) : RssFeedResult {
        override val isEmpty: Boolean
            get() = feed.channel?.items?.isEmpty() ?: true
    }

    data class Zoomit(val feed: ZoomitRssFeed) : RssFeedResult {
        override val isEmpty: Boolean
            get() = feed.channel?.items?.isEmpty() ?: true
    }
}