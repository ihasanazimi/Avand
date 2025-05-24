package ir.hasanazimi.avand.presentation.screens.news

import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.NewsItemUtils
import ir.hasanazimi.avand.data.entities.remote.news.NewsItemWrapper
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.RssFeedResult
import ir.hasanazimi.avand.presentation.itemViews.NewsItemView
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun NewsScreen(
    activity: MainActivity,
    navController: NavHostController,
    showMoreBtn: Boolean = false,
    onMoreNews : () -> Unit,
    showPaginationLoading: (Boolean) -> Unit,
    onOpenWebView: (newsUrl: String) -> Unit,
) {

    val viewModel = hiltViewModel<NewsScreenVM>()
    var news = viewModel.newsResponse.collectAsStateWithLifecycle()


    AvandTheme {
        NewsContent(
            viewModel = viewModel,
            activity = activity,
            showMoreBtn = showMoreBtn,
            onMoreNews = onMoreNews,
            news = news,
            showPaginationLoading = {
                showPaginationLoading.invoke(it)
            },
            onOpenWebView = onOpenWebView,
        )
    }

}


@Composable
private fun NewsContent(
    viewModel: NewsScreenVM?,
    activity: MainActivity,
    showMoreBtn: Boolean = false,
    onMoreNews : () -> Unit,
    news: State<ResponseState<List<RssFeedResult?>>>,
    showPaginationLoading: (Boolean) -> Unit,
    onOpenWebView: (String) -> Unit,
) {

    var masterList by remember { mutableStateOf<List<NewsItemWrapper>>(arrayListOf())}

    Box{
        Column(modifier = Modifier.fillMaxWidth()) {

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {

                Text(
                    text = "اخبار روز",
                    style = CustomTypography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterEnd),
                )

                if (news.value is ResponseState.Success) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterStart)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                viewModel?.getNewsRss(true)
                            }
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            modifier = Modifier
                                .size(20.dp)
                                .fillMaxWidth()
                                .padding(start = 4.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )


                        Text(
                            text = "بروزرسانی",
                            style = CustomTypography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp),
                        )

                    }
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                if (news.value is ResponseState.Error) {
                    ErrorMode(onRefresh = { viewModel?.getNewsRss(true) })
                }

                if (news.value is ResponseState.Loading) {
                    LoadingMode(modifier = Modifier.fillMaxWidth())
                }

            }


            if (news.value is ResponseState.Success) {
                val results = news.value.data
                val newsItems = results?.flatMapIndexed { index, result ->
                    when (result) {
                        is RssFeedResult.KhabarOnline -> result.feed.channel.items?.map {
                            NewsItemWrapper.KhabarOnline(it, NewsSources.KHABAR_ONLINE)
                        } ?: emptyList()

                        is RssFeedResult.Zoomit -> result.feed.channel?.items?.map {
                            NewsItemWrapper.Zoomit(it, NewsSources.ZOOMIT)
                        } ?: emptyList()

                        null -> emptyList()
                    }
                } ?: emptyList()

                masterList = newsItems
                masterList.forEachIndexed { index , item ->
                    if (showMoreBtn){
                        Log.i("TAG", "NewsScreen new item -> $item")
                        if (index > 10) return@forEachIndexed
                        NewsItemView(
                            news = item,
                            onNewsClick = { newsItem ->
                                Log.d("NewsScreen", "Clicked getTitle: ${NewsItemUtils.getTitle(newsItem.item)}")
                                Log.d("NewsScreen", "Clicked getDescription: ${NewsItemUtils.getDescription(newsItem.item)}")
                                Log.d("NewsScreen", "Clicked getPublishDate: ${NewsItemUtils.getPublishDate(newsItem.item)}")
                                Log.d("NewsScreen", "Clicked getImageUrl: ${NewsItemUtils.getImageUrl(newsItem.item)}")
                                Log.d("NewsScreen", "Clicked getLink: ${NewsItemUtils.getLink(newsItem.item)}")
                                onOpenWebView.invoke(NewsItemUtils.getLink(item.item) ?: "")
                            },
                            onShareNews = { item ->
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, NewsItemUtils.getLink(item.item))
                                    type = "text/plain"
                                }
                                activity.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "اشتراک خبر از آوند "
                                    )
                                )
                            }
                        )
                    }else{

                        PaginatedNewsList(
                            originalList = masterList,
                            activity = activity,
                            openWebView = onOpenWebView,
                            showPaginationLoading = {
                                showPaginationLoading.invoke(it)
                            }
                        )
                    }
                }


                if (showMoreBtn) {

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onMoreNews.invoke()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "مشاهده بیشتر",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(16.dp)
                            )

                            Text(
                                text = "مشاهده بیشتر",
                                textAlign = TextAlign.Center,
                                style = CustomTypography.labelSmall,
                                modifier = Modifier
                                    .padding(start = 4.dp, bottom = 8.dp, top = 8.dp, end = 16.dp)
                            )
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) { }
    }
}

@Composable
private fun PaginationLoading() {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )

    Box(
        modifier = Modifier
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.loading),
            contentDescription = "در حال بارگذاری",
            modifier = Modifier
                .padding(top = 8.dp)
                .size(32.dp)
                .graphicsLayer { rotationZ = rotation },
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}


@Composable
fun PaginatedNewsList(
    originalList: List<NewsItemWrapper>,
    activity: MainActivity,
    openWebView: (String) -> Unit,
    showPaginationLoading : (show : Boolean) -> Unit
) {
    val pageSize = 10 // تعداد آیتم‌های هر صفحه
    var displayedList by remember { mutableStateOf(originalList.take(pageSize)) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // بررسی اسکرول به انتهای لیست برای بارگذاری بیشتر
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= totalItemsCount - 1 && totalItemsCount < originalList.size
        }.collect { shouldLoadMore ->
            if (shouldLoadMore) {
                // اضافه کردن آیتم‌های جدید به displayedList
                showPaginationLoading.invoke(true)
                delay(1000)
                displayedList = originalList.take(displayedList.size + pageSize)
                delay(1000)
                showPaginationLoading.invoke(false)
            }
        }
    }

    // رندر لیست
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(displayedList) { item ->
            NewsItemView(
                news = item,
                onNewsClick = { newsItem ->
                    Log.d("NewsScreen", "Clicked getTitle: ${NewsItemUtils.getTitle(newsItem.item)}")
                    Log.d("NewsScreen", "Clicked getDescription: ${NewsItemUtils.getDescription(newsItem.item)}")
                    Log.d("NewsScreen", "Clicked getPublishDate: ${NewsItemUtils.getPublishDate(newsItem.item)}")
                    Log.d("NewsScreen", "Clicked getImageUrl: ${NewsItemUtils.getImageUrl(newsItem.item)}")
                    Log.d("NewsScreen", "Clicked getLink: ${NewsItemUtils.getLink(newsItem.item)}")
                    openWebView.invoke(NewsItemUtils.getLink(newsItem.item) ?: "")
                },
                onShareNews = { newsItem ->
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, NewsItemUtils.getLink(newsItem.item))
                        type = "text/plain"
                    }
                    activity.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "اشتراک خبر از آوند"
                        )
                    )
                }
            )
        }
    }

    DisposableEffect(coroutineScope) {
        onDispose {
            coroutineScope.cancel()
        }
    }

}


@Composable
private fun ErrorMode(onRefresh: () -> Unit = {}) {

    Log.i("ErrorStateOnNews", "ErrorStateOnNews: ")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_error_outline_24),
                contentDescription = "خطا",
                modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.CenterHorizontally),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )

            Text(
                text = "خطا در برقراری ارتباط",
                textAlign = TextAlign.Center,
                style = CustomTypography.labelSmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onRefresh() },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "تلاش مجدد",
                        textAlign = TextAlign.Center,
                        style = CustomTypography.labelSmall,
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 8.dp, top = 8.dp, end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "بروزرسانی",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingMode(modifier: Modifier) {

    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )

    Box(
        modifier = modifier
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.loading),
                contentDescription = "در حال بارگذاری",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(32.dp)
                    .graphicsLayer { rotationZ = rotation },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Text(
                text = "کمی صبر کنید...",
                textAlign = TextAlign.Center,
                style = CustomTypography.labelLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsScreenPreView() {
    AvandTheme {
        val state = MutableSharedFlow<ResponseState.Success<List<RssFeedResult>>>()
        NewsContent(
            viewModel = null,
            activity = MainActivity(),
            news = state.collectAsState(ResponseState.Error(Exception())),
            showMoreBtn = false,
            onMoreNews = {},
            showPaginationLoading = {

            },
            onOpenWebView = {},
        )
    }
}