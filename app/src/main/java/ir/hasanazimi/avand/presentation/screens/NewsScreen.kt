package ir.hasanazimi.avand.presentation.screens

import android.content.Context
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.NewsItemWrapper
import ir.hasanazimi.avand.data.entities.remote.news.NewsItemUtils
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.RssFeedResult
import ir.hasanazimi.avand.presentation.dialogs.Wide70PercentHeightDialog
import ir.hasanazimi.avand.presentation.itemViews.CustomSpacer
import ir.hasanazimi.avand.presentation.itemViews.NewsItemView
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun NewsScreen(
    activity: MainActivity,
    navController: NavHostController,
    newsState: ResponseState<List<RssFeedResult?>>?,
    onRefresh: () -> Unit
) {
    var newsUrl by remember { mutableStateOf<String>("") }
    var showNewsLoading by remember { mutableStateOf(false) }
    var showNewsError by remember { mutableStateOf(false) }

    AvandTheme {


        Spacer(modifier = Modifier.padding(top = 16.dp))

        CustomSpacer()

        Column(modifier = Modifier.fillMaxWidth()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                Text(
                    text = "اخبار روز",
                    style = CustomTypography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                            top = 8.dp
                        )
                        .align(Alignment.CenterEnd),
                )


                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onRefresh() },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "location",
                        modifier = Modifier
                            .size(20.dp)
                            .fillMaxWidth(),
                        tint = MaterialTheme.colorScheme.secondary
                    )


                    Text(
                        text = "بروزرسانی",
                        style = CustomTypography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }

            }
        }


        when (newsState) {
            is ResponseState.Success -> {

                val results = newsState.data
                val newsItems = results?.flatMapIndexed { index, result ->
                    when (result) {
                        is RssFeedResult.KhabarOnline -> result.feed.channel?.items?.map {
                            NewsItemWrapper.KhabarOnline(it, NewsSources.KHABAR_ONLINE)
                        } ?: emptyList()

                        is RssFeedResult.Zoomit -> result.feed.channel?.items?.map {
                            NewsItemWrapper.Zoomit(it, NewsSources.ZOOMIT)
                        } ?: emptyList()
                        null -> emptyList()
                    }
                } ?: emptyList()

                newsItems.filter {
                    NewsItemUtils.getLink(it.item) != null &&
                    NewsItemUtils.getTitle(it.item) != null
                }.forEach { item ->
                    Log.i("TAG", "NewsScreen new item -> $item")
                    NewsItemView(
                        news = item,
                        onNewsClick = { item ->
                            Log.d(
                                "NewsScreen",
                                "Clicked getTitle: ${NewsItemUtils.getTitle(item.item)}"
                            )
                            Log.d(
                                "NewsScreen",
                                "Clicked getDescription: ${NewsItemUtils.getDescription(item.item)}"
                            )
                            Log.d(
                                "NewsScreen",
                                "Clicked getPublishDate: ${NewsItemUtils.getPublishDate(item.item)}"
                            )
                            Log.d(
                                "NewsScreen",
                                "Clicked getImageUrl: ${NewsItemUtils.getImageUrl(item.item)}"
                            )
                            Log.d(
                                "NewsScreen",
                                "Clicked getLink: ${NewsItemUtils.getLink(item.item)}"
                            )
                            newsUrl = NewsItemUtils.getLink(item.item) ?: ""
                        },
                        onShareNews = { item ->
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, NewsItemUtils.getLink(item.item))
                                type = "text/plain"
                            }
                            activity.startActivity(Intent.createChooser(shareIntent, "اشتراک خبر از آوند "))
                        }
                    )
                }

                showNewsError = false
                showNewsLoading = false
            }

            is ResponseState.Loading -> {
                showNewsLoading = true
                showNewsError = false
            }

            is ResponseState.Error -> {
                showNewsLoading = false
                showNewsError = true
            }

            else -> {
                showNewsLoading = false
                showNewsError = false
            }
        }

        if (newsUrl.isNotEmpty()) {
            Wide70PercentHeightDialog(
                onDismissRequest = { newsUrl = "" },
                content = {
                    WebViewScreen(
                        url = newsUrl,
                    ) {
                        newsUrl = ""
                    }
                }
            )
        }

        if (showNewsLoading) {
            LoadingBox()
        }

        if (showNewsError) {
            ErrorStateOnNews(
                context = LocalContext.current,
                exception = (newsState as? ResponseState.Error)?.exception
            ) {
                onRefresh()
            }
        }
    }
}

@Composable
fun ErrorStateOnNews(context: Context, exception: Exception?, onRefresh: () -> Unit = {}) {
    exception?.let {
        showToast(context, message = it.message ?: "خطای ناشناخته")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
fun LoadingBox() {
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
            .fillMaxSize()
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
        NewsScreen(
            activity = MainActivity(),
            navController = rememberNavController(),
            newsState = null
        ) { }
    }
}