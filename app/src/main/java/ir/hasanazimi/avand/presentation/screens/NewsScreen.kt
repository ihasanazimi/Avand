package ir.hasanazimi.avand.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.Item
import ir.hasanazimi.avand.presentation.dialogs.Wide70PercentHeightDialog
import ir.hasanazimi.avand.presentation.itemViews.CustomSpacer
import ir.hasanazimi.avand.presentation.itemViews.NewsItemView
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun NewsScreen(
    navController: NavHostController,
    newsData: ResponseState<List<Item>>?,
    onRefresh: () -> Unit
) {
    var newsUrl by remember { mutableStateOf<String>("") }
    var webViewDialogIsShow by remember { mutableStateOf(false) }
    var newsLoading by remember { mutableStateOf(false) }

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
                    text = "اخبار روز :",
                    style = CustomTypography.titleLarge,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                            top = 8.dp
                        )
                        .align(Alignment.CenterEnd),
                )


                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "location",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterStart)
                        .size(24.dp)
                        .fillMaxWidth()
                        .clickable { onRefresh() },
                    tint = MaterialTheme.colorScheme.secondary
                )

            }
        }

        when (newsData) {
            is ResponseState.Success -> {
                newsLoading = false
                newsData.data?.forEach {
                    NewsItemView(it) { clickedNews ->
                        newsUrl = clickedNews.link ?: ""
                        webViewDialogIsShow = true
                    }
                }
            }

            is ResponseState.Error -> {
                newsLoading = false
                showToast(LocalContext.current, "خطای نامشخص")
            }

            is ResponseState.Loading -> {
                newsLoading = true
            }

            else -> {
                newsLoading = false
            }
        }


        if (newsUrl.isNotEmpty()) {
            Wide70PercentHeightDialog(
                onDismissRequest = { newsUrl = "" },
                content = {
                    WebViewScreen(
                        url = newsUrl,
                        isShow = webViewDialogIsShow
                    ) {
                        newsUrl = ""
                        webViewDialogIsShow = false
                    }
                }
            )
        }


        if (newsLoading) {
            LoadingBox()
        }


    }
}

@Composable
private fun LoadingBox() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loading)
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(120.dp)
        )
    }
}

@Preview
@Composable
private fun NewsScreenPreview() {
    AvandTheme {
        NewsScreen(
            navController = rememberNavController(),
            newsData = ResponseState.Success(emptyList()),
            onRefresh = {

            },
        )
    }
}