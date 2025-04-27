package ir.hasanazimi.avand.data.repository

import android.util.Log
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.Item
import ir.hasanazimi.avand.data.web_services.news.RssService
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject


interface NewsRssRepository {

    suspend fun getNews() : Flow<ResponseState<List<Item>>>

}


class NewsRssRepositoryImpl @Inject constructor(
    private val rssService : RssService,
    private val dataStoreManager: DataStoreManager
) : NewsRssRepository {

    val TAG = "NewsRssRepositoryImpl"
    override suspend fun getNews(): Flow<ResponseState<List<Item>>> = flow{
        emit(ResponseState.Loading)
        try {
            val result = rssService.getRssFeed()
            if (result.isSuccessful){
                val newsItems = result.body()?.channel?.items?:emptyList()
                emit(ResponseState.Success(newsItems))
                Log.i(TAG, "getNews: $newsItems")
            }
        }catch (e : IOException){
            emit(ResponseState.Error(e))
        }
    }
}
