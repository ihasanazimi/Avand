package ir.ha.goodfeeling.screens

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
import ir.ha.goodfeeling.data.models.entities.NewsItemEntity
import ir.ha.goodfeeling.data.models.other.getFakeNews
import ir.ha.goodfeeling.screens.itemViews.NewsItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@Composable
fun NewsScreen(navController: NavHostController, onMoreBtnClick: () -> Unit) {

    val newsList: ArrayList<NewsItemEntity> = getFakeNews()
    val takeCount = newsList.take(3).size
    var url by remember { mutableStateOf<String>("") }
    var webViewDialogIsShow by remember { mutableStateOf(false) }

    GoodFeelingTheme {

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
                        url = news.link
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
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    if (url.isNotEmpty()){
        FullScreenDialog(
            onDismissRequest = { url = "" },
            content = {
                WebViewScreen(
                    url = url,
                    isShow = webViewDialogIsShow
                ) {
                    url = ""
                    webViewDialogIsShow = false
                    navController.popBackStack()
                }
            }
        )
    }
}


@Preview
@Composable
private fun NewsScreenPreview() {
    GoodFeelingTheme {
        NewsScreen(rememberNavController()) {}
    }
}