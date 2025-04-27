package ir.hasanazimi.avand.use_cases

import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.Item
import ir.hasanazimi.avand.data.repository.NewsRssRepository
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRssUseCase {
    suspend fun getNews() : Flow<ResponseState<List<Item>>>
}


class NewsUseCaseImpl @Inject constructor(
    private val newsRssRepository: NewsRssRepository,
    dataStoreManager: DataStoreManager
) : NewsRssUseCase{

    override suspend fun getNews(): Flow<ResponseState<List<Item>>>{
        return newsRssRepository.getNews()
    }
}