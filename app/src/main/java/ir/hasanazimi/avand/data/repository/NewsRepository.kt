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
                    sources.map { source ->
                        async {
                            try {
                                apiCall(rssService, source)
                            } catch (e: Exception) {
                                Log.e(TAG, "apiCall failed for ${source.baseUrl}: ${e.message}")
                                null
                            }
                        }
                    }.map { it.await() }
                }

                val successfulResults = results.filterNotNull()

                if (successfulResults.isNotEmpty()) {
                    Log.i(TAG, "getAllNews responseStateSuccess is -> : $successfulResults")
                    emit(ResponseState.Success(successfulResults))
                } else {
                    Log.e(TAG, "All API calls failed")
                    emit(ResponseState.Error(Exception("All news sources failed to load")))
                }
            } catch (e: Exception) {
                Log.e(TAG, "getAllNews: ${e.message}")
                emit(ResponseState.Error(e))
            }
        }
}

private suspend fun apiCall(rssService: RssService, newsSources: NewsSources): RssFeedResult {
    return when (newsSources) {
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
}
