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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.data.entities.NewsItemEntity
import ir.ha.goodfeeling.data.getFakeNews
import ir.ha.goodfeeling.screens.itemViews.NewsItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.Gray
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue

@Composable
fun NewsScreen(news: ArrayList<NewsItemEntity> , takeCount: Int = 3) {
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
                    text = "اخبار روز",
                    style = CustomTypography.bodyLarge,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                            top = 16.dp
                        )
                        .align(Alignment.End),
                )

                news.take(takeCount).forEachIndexed { index, item ->
                    if (index + 1 < takeCount) {
                        NewsItemView(item)
                    } else {
                        NewsItemView(item)
                        Button(
                            enabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .width(200.dp)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TransparentlyBlue),
                            onClick = {

                            }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "بیشتر",
                                    style = CustomTypography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    color = LightPrimary
                                )
                            }

                        }
                    }
                }
            }
        }


    }
}


@Preview
@Composable
private fun NewsScreenPreview() {
    GoodFeelingTheme {
        NewsScreen(getFakeNews())
    }
}