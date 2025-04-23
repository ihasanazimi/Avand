package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.NewsItem
import ir.hasanazimi.avand.data.repository.NewsRssRepository
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRssUseCase {
    suspend fun getNews(q : String) : Flow<ResponseState<List<NewsItem>>>
}


class NewsUseCaseImpl @Inject constructor(
    private val newsRssRepository: NewsRssRepository,
    dataStoreManager: DataStoreManager
) : NewsRssUseCase{

    override suspend fun getNews(q: String): Flow<ResponseState<List<NewsItem>>>{
        return newsRssRepository.getNews(q)
    }
}