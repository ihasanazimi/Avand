package ir.hasanazimi.avand.presentation.itemViews

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
                .padding(bottom = 8.dp, top = 8.dp)
                .fillMaxWidth()
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    RoundedCornerShape(16.dp)
                )
                .clickable { onNewsClick.invoke(news) }, shape = RoundedCornerShape(16.dp)
        ) {
            Column {

                Card(
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        topStart = 12.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 12.dp, top = 12.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp, 2.dp, 2.dp, 2.dp, 2.dp, 0.dp)
                ) {
                    ImageLoading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        url = NewsItemUtils.getImageUrl(news.item)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = NewsItemUtils.getTitle(news.item) ?: "بدون عنوان",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            style = CustomTypography.titleLarge.copy(
                                lineHeight = TextUnit(32f, TextUnitType.Sp)
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = NewsItemUtils.getDescription(news.item) ?: "-_-",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
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
                            .padding(16.dp)
                            .align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "اشتراک خبر",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(28.dp)
                                .padding(5.dp)
                                .clickable {
                                    onShareNews.invoke(news)
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp, start = 8.dp)
                            .align(Alignment.CenterEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NewsItemUtils.getPublishDate(news.item) ?: "نامشخص",
                            style = CustomTypography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp)
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