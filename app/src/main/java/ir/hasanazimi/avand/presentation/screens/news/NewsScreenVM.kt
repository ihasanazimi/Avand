package ir.hasanazimi.avand.presentation.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.RssFeedResult
import ir.hasanazimi.avand.use_cases.NewsRssUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsScreenVM @Inject constructor(
    private val newsRssUseCase: NewsRssUseCase
) : ViewModel(){


    private val _newsResponse = MutableStateFlow<ResponseState<List<RssFeedResult?>>>(ResponseState.Loading)
    val newsResponse = _newsResponse.asStateFlow()

    fun getNewsRss() {
        viewModelScope.launch {

            val feeds = listOf(
                NewsSources.KHABAR_ONLINE,
                NewsSources.KHABAR_ONLINE_IT,
                NewsSources.ZOOMIT,
                NewsSources.KHABAR_ONLINE_SIYASI_EGTESAGI,
            )

            newsRssUseCase.getAllNews(feeds).collect { result ->
                when (result) {
                    is ResponseState.Success -> {
                        _newsResponse.emit(ResponseState.Success(result.data))
                    }
                    is ResponseState.Error -> {
                        result.exception?.let { _newsResponse.emit(ResponseState.Error(it)) }
                    }
                    is ResponseState.Loading -> {
                        _newsResponse.emit(ResponseState.Loading)
                    }
                }
            }
        }
    }


}