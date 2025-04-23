package ir.hasanazimi.avand.data.repository

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.NewsItem
import ir.hasanazimi.avand.data.web_services.news.NewsRssWebService
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject


interface NewsRssRepository {

    suspend fun getNews(q : String) : Flow<ResponseState<List<NewsItem>>>

}


class NewsRssRepositoryImpl @Inject constructor(
    private val newsRssWebService : NewsRssWebService,
    private val dataStoreManager: DataStoreManager
) : NewsRssRepository {

    override suspend fun getNews(q: String) = flow{
        emit(ResponseState.Loading)
        try {
            val result = newsRssWebService.getNews("ایران")
            if (result.isSuccessful){
                val newsItems = result.body()?.channel?.items?:emptyList()
                emit(ResponseState.Success(newsItems))
            }
        }catch (e : IOException){
            emit(ResponseState.Error(e))
        }
    }
}
