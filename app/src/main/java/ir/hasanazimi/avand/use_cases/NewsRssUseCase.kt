package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.RssFeedResult
import ir.hasanazimi.avand.data.repository.NewsRssRepository
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface NewsRssUseCase {
    suspend fun getNews(newsSources: NewsSources): Flow<ResponseState<RssFeedResult?>>
    suspend fun getAllNews(sources: List<NewsSources>): Flow<ResponseState<List<RssFeedResult?>>>
}

class NewsRssUseCaseImpl @Inject constructor(
    private val repository: NewsRssRepository,
    private val dataStoreManager: DataStoreManager
) : NewsRssUseCase {
    override suspend fun getNews(newsSources: NewsSources): Flow<ResponseState<RssFeedResult?>> {
        return repository.getNews(newsSources)
    }

    override suspend fun getAllNews(sources: List<NewsSources>): Flow<ResponseState<List<RssFeedResult?>>> {
        return repository.getAllNews(sources)
    }
}