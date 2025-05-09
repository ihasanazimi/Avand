package ir.hasanazimi.avand.data.repository


import android.util.Log
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.RssFeedResult
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Channel
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.RssFeed
import ir.hasanazimi.avand.data.web_services.news.RssService
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

interface NewsRssRepository {
    suspend fun getNews(newsSources: NewsSources): Flow<ResponseState<RssFeedResult?>>
    suspend fun getAllNews(sources: List<NewsSources>): Flow<ResponseState<List<RssFeedResult?>>>
}

class NewsRssRepositoryImpl @Inject constructor(
    private val rssService: RssService,
    private val dataStoreManager: DataStoreManager
) : NewsRssRepository {

    val TAG = this::class.simpleName

    override suspend fun getNews(newsSources: NewsSources): Flow<ResponseState<RssFeedResult>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = apiCall(rssService, newsSources)
            emit(ResponseState.Success(result))
        } catch (e: Exception) {
            emit(ResponseState.Error(e))
        }
    }

    override suspend fun getAllNews(sources: List<NewsSources>): Flow<ResponseState<List<RssFeedResult?>>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val results = coroutineScope {
                    sources.map { source -> async { apiCall(rssService, source) } }.map { it.await() }
                }
                Log.i(TAG, "getAllNews responseStateSuccess is -> : $results")
                emit(ResponseState.Success(results))
            } catch (e: Exception) {
                Log.e(TAG, "getAllNews: ${e.message}")
                emit(ResponseState.Error(e))
            }
        }
}


private suspend fun apiCall(rssService: RssService, newsSources: NewsSources): RssFeedResult = try {
    when (newsSources) {
        NewsSources.KHABAR_ONLINE_SIYASI_EGTESAGI -> RssFeedResult.KhabarOnline(
            rssService.getKhabarOnlineRssFeed(newsSources.baseUrl)
        ).also {
            Log.i("NewsRssRepositoryImpl", "apiCall: url -> ${newsSources.baseUrl}")
            Log.i(
                "NewsRssRepositoryImpl",
                "apiCall KhabarOnline 1: ${it.feed.channel?.items?.size}"
            )
        }

        NewsSources.KHABAR_ONLINE_IT -> RssFeedResult.KhabarOnline(
            rssService.getKhabarOnlineRssFeed(newsSources.baseUrl)
        ).also {
            Log.i("NewsRssRepositoryImpl", "apiCall: url -> ${newsSources.baseUrl}")
            Log.i(
                "NewsRssRepositoryImpl",
                "apiCall KhabarOnline 2: ${it.feed.channel?.items?.size}"
            )
        }

        NewsSources.KHABAR_ONLINE -> RssFeedResult.KhabarOnline(
            rssService.getKhabarOnlineRssFeed(newsSources.baseUrl)
        ).also {
            Log.i("NewsRssRepositoryImpl", "apiCall: url -> ${newsSources.baseUrl}")
            Log.i(
                "NewsRssRepositoryImpl",
                "apiCall KhabarOnline 3: ${it.feed.channel?.items?.size}"
            )
        }

        NewsSources.ZOOMIT -> RssFeedResult.Zoomit(
            rssService.getZoomitRssFeed(newsSources.baseUrl)
        ).also {
            Log.i("NewsRssRepositoryImpl", "apiCall: url -> ${newsSources.baseUrl}")
            Log.i(
                "NewsRssRepositoryImpl",
                "apiCall getZoomitRssFeed: ${it.feed.channel?.items?.size}"
            )
        }
    }
} catch (e: Exception) {
    Log.e("NewsRssRepositoryImpl", "apiCall failed for ${newsSources.baseUrl}: ${e.message}")
    // در صورت خطا، یک آبجکت خالی برمی‌گردانیم
    when (newsSources) {
        NewsSources.KHABAR_ONLINE_SIYASI_EGTESAGI,
        NewsSources.KHABAR_ONLINE_IT,
        NewsSources.KHABAR_ONLINE -> RssFeedResult.KhabarOnline(
            RssFeed(channel = Channel(items = emptyList())) // فرض بر این است که Channel و items وجود دارند
        )

        NewsSources.ZOOMIT -> RssFeedResult.Zoomit(
            ir.hasanazimi.avand.data.entities.remote.news.zoomit.RssFeed(
                channel = ir.hasanazimi.avand.data.entities.remote.news.zoomit.Channel(
                    items = emptyList()
                )
            ) // فرض بر این است که Channel و items وجود دارند
        )
    }
}
