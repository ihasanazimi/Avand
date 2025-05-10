package ir.hasanazimi.avand.presentation.itemViews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hasanazimi.avand.data.entities.remote.news.NewsItemWrapper
import ir.hasanazimi.avand.data.entities.remote.news.NewsItemUtils
import ir.hasanazimi.avand.data.entities.remote.news.NewsSources
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Category
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Enclosure
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Item
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Source
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun NewsItemView(
    news: NewsItemWrapper,
    onNewsClick: (news: NewsItemWrapper) -> Unit,
    onShareNews: (news: NewsItemWrapper) -> Unit
) {
    AvandTheme {
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    RoundedCornerShape(24.dp)
                )
                .clickable { onNewsClick.invoke(news) }, shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 26.dp,
                    bottom = 24.dp
                )
            ) {

                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(1.dp, 1.dp, 1.dp, 1.dp, 1.dp, 1.dp)
                ) {
                    ImageLoading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        url = NewsItemUtils.getImageUrl(news.item)
                    )
                }


                Box {
                    Text(
                        text = NewsItemUtils.getCategory(news.item) ?: "-_-",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        style = CustomTypography.labelSmall.copy(
                            lineHeight = TextUnit(24f, TextUnitType.Sp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        ),
                        maxLines = 1
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = NewsItemUtils.getTitle(news.item) ?: "بدون عنوان",
                            modifier = Modifier
                                .fillMaxWidth(),
                            style = CustomTypography.titleLarge.copy(
                                lineHeight = TextUnit(32f, TextUnitType.Sp)
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = NewsItemUtils.getDescription(news.item) ?: "-_-",
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            style = CustomTypography.labelLarge.copy(
                                lineHeight = TextUnit(24f, TextUnitType.Sp)
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "اشتراک خبر",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(32.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                    CircleShape
                                )
                                .padding(end = 6.dp, start = 2.dp, top = 2.dp, bottom = 2.dp)
                                .clickable {
                                    onShareNews.invoke(news)
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NewsItemUtils.getPublishDate(news.item) ?: "نامشخص",
                            style = CustomTypography.labelSmall.copy(color = MaterialTheme.colorScheme.primary),
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun NewsItemViewPreview() {
    AvandTheme {
        NewsItemView(
            news = NewsItemWrapper.KhabarOnline(
                Item(
                    title = "title",
                    link = "sdjfhksjdfh",
                    description = "description",
                    contentEncoded = "pubDate",
                    source = Source("dshflk", "sldfjlkdf"),
                    enclosure = Enclosure("dsjfl", "djflds", "fdjkhf"),
                    category = Category("dsf", "sdfdsf"),
                    pubDate = "Fri, 09 May 2025 17:50:00 GMT",
                    guid = "dsf"
                ), NewsSources.KHABAR_ONLINE
            ),
            onNewsClick = {},
            onShareNews = {

            }
        )
    }
}