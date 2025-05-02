package ir.hasanazimi.avand.data.entities.remote

import ir.hasanazimi.avand.data.entities.remote.news.NewsSources

sealed interface NewsItemWrapper {
    val item: Any
    val source: NewsSources
    data class KhabarOnline(override val item: ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Item, override val source: NewsSources) : NewsItemWrapper
    data class Zoomit(override val item: ir.hasanazimi.avand.data.entities.remote.news.zoomit.Item, override val source: NewsSources) : NewsItemWrapper
}