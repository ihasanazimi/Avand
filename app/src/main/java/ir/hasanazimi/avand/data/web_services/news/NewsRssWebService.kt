package ir.hasanazimi.avand.data.web_services.news

import ir.hasanazimi.avand.data.entities.remote.news.GoogleNewsRss
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRssWebService {
    @GET("rss/search")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("hl") language: String = "fa",
        @Query("gl") country: String = "IR"
    ): Response<GoogleNewsRss>
}