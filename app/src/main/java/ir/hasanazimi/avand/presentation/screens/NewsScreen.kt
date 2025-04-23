package ir.hasanazimi.avand.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.data.getFakeNews
import ir.hasanazimi.avand.data.models.local_entities.other.NewsItemEntity
import ir.hasanazimi.avand.presentation.dialogs.Wide70PercentHeightDialog
import ir.hasanazimi.avand.presentation.itemViews.NewsItemView
import ir.hasanazimi.avand.ui.theme.AvandTheme
import ir.hasanazimi.avand.ui.theme.CustomTypography

@Composable
fun NewsScreen(navController: NavHostController, onMoreBtnClick: () -> Unit) {

    val newsList: ArrayList<NewsItemEntity> = getFakeNews()
    val takeCount = newsList.take(3).size
    var newsUrl by remember { mutableStateOf<String>("") }
    var webViewDialogIsShow by remember { mutableStateOf(false) }

    AvandTheme {

        Surface {
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

                newsList.take(takeCount).forEachIndexed { index, item ->

                    NewsItemView(item) { news ->
                        /*val encodedUrl = Uri.encode(news.link)*/
                        /*navController.navigate(Screens.WebView.route + "/$encodedUrl")*/
                        newsUrl = news.link
                        webViewDialogIsShow = true
                    }

                    if (index + 1 >= takeCount) {
                        Button(
                            enabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                                .width(200.dp)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.3f
                                )
                            ),
                            onClick = {
                                onMoreBtnClick.invoke()
                            }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "بیشتر",
                                    style = CustomTypography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                            }

                        }
                    }
                }
            }

            if (newsUrl.isNotEmpty()){
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
}


@Preview
@Composable
private fun NewsScreenPreview() {
    AvandTheme {
        NewsScreen(rememberNavController()) {}
    }
}