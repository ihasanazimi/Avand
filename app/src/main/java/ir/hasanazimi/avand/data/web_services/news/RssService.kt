package ir.hasanazimi.avand.data.web_services.news

import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {

    @GET
    suspend fun getEgtesadOnlineRssFeed(@Url url: String): ir.hasanazimi.avand.data.entities.remote.news.egtesad_online.RssFeed

    @GET
    suspend fun getKhabarOnlineRssFeed(@Url url: String): ir.hasanazimi.avand.data.entities.remote.news.khabar_online.RssFeed

    @GET
    suspend fun getZoomitRssFeed(@Url url: String): ir.hasanazimi.avand.data.entities.remote.news.zoomit.RssFeed

}