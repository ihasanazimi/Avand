package ir.hasanazimi.avand.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.remote.news.Item
import ir.hasanazimi.avand.presentation.dialogs.Wide70PercentHeightDialog
import ir.hasanazimi.avand.presentation.itemViews.NewsItemView
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun NewsScreen(
    navController: NavHostController,
    newsData: ResponseState<List<Item>>?,
    onMoreBtnClick: () -> Unit
) {
    var newsUrl by remember { mutableStateOf<String>("") }
    var webViewDialogIsShow by remember { mutableStateOf(false) }

    AvandTheme {

        Column(modifier = Modifier.fillMaxWidth()) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "اخبار روز :",
                style = CustomTypography.titleLarge,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                        top = 16.dp
                    )
                    .align(Alignment.End),
            )


        }

        when(newsData){
            is ResponseState.Success -> {
                newsData.data?.forEach {
                    NewsItemView(it) { clickedNews ->
                        /*val encodedUrl = Uri.encode(news.link)*/
                        /*navController.navigate(Screens.WebView.route + "/$encodedUrl")*/
                        newsUrl = clickedNews.link ?: ""
                        webViewDialogIsShow = true
                    }
                }
            }
            is ResponseState.Error -> {

            }
            is ResponseState.Loading -> {

            }
            else -> {

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



    }
}

@Preview
@Composable
private fun NewsScreenPreview() {
    AvandTheme {
        NewsScreen(
            navController = rememberNavController(),
            newsData = ResponseState.Success(emptyList()),
            onMoreBtnClick = {

            },
        )
    }
}