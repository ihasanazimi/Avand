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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.hasanazimi.avand.data.entities.remote.news.Item
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun NewsItemView(news: Item, onNewsClick : (newItemEntity : Item) -> Unit) {
    AvandTheme {
        Surface{
            Column(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 8.dp)
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)).clickable{
                        onNewsClick.invoke(news)
                    }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
                ) {

                    AsyncImage(
                        model = news.enclosure?.url,
                        contentDescription = news.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
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
                            text = news.title ?:"-_-",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            style = CustomTypography.titleLarge
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = news.description?:"-_-",
                            modifier = Modifier.fillMaxWidth(),
                            style = CustomTypography.labelSmall.copy(
                                lineHeight = TextUnit(
                                    20f,
                                    TextUnitType.Sp
                                )
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
                            contentDescription = "share prices",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(28.dp)
                                .padding(5.dp)
                                .clickable {
                                    // todo
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                        /*Row(
                            modifier = Modifier
                                .padding(end = 8.dp, start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "share prices",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(28.dp)
                                    .padding(2.dp)
                                    .clickable {
                                        // todo
                                    },
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = newsItem.seenCount.toString().getAmountFormatBySeparator(),
                                style = CustomTypography.labelSmall,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }*/
                    }


                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp, start = 8.dp)
                            .align(Alignment.CenterEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = news.pubDate?:"-_-",
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

    }
}