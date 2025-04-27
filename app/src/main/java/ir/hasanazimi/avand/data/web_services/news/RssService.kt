package ir.hasanazimi.avand.data.web_services.news

import ir.hasanazimi.avand.data.entities.remote.news.RssFeed
import retrofit2.Response
import retrofit2.http.GET

interface RssService {
    @GET("rss")
    suspend fun getRssFeed(): Response<RssFeed>
}