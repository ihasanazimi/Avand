package ir.ha.goodfeeling.screens.itemViews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.common.extensions.getAmountFormatBySeparator
import ir.ha.goodfeeling.data.entities.NewsItemEntity
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.DarkBackground
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightBackground
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue
import ir.ha.goodfeeling.ui.theme.TransparentlyGray

@Composable
fun NewsItemView(newsItemEntity: NewsItemEntity) {
    GoodFeelingTheme {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp, top = 8.dp)
                .fillMaxWidth()
                .border(2.dp, TransparentlyBlue, RoundedCornerShape(16.dp))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) DarkBackground else LightBackground),
                shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
            ) {
                Image(
                    painter = painterResource(newsItemEntity.cover),
                    contentDescription = newsItemEntity.title,
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
                    .background(color = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = newsItemEntity.title,
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
                        text = newsItemEntity.description,
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
                    .background(Color.Transparent)
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
                            .background(TransparentlyBlue)
                            .size(28.dp)
                            .padding(5.dp)
                            .clickable {
                                // todo
                            },
                        tint = LightPrimary
                    )
                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp, start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "share prices",
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(TransparentlyBlue)
                                .size(28.dp)
                                .padding(2.dp)
                                .clickable {
                                    // todo
                                },
                            tint = LightPrimary
                        )

                        Text(
                            text = newsItemEntity.seenCount.toString().getAmountFormatBySeparator(),
                            style = CustomTypography.labelSmall.copy(color = LightPrimary),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .padding(end = 8.dp, start = 8.dp)
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = newsItemEntity.timeAgo,
                        style = CustomTypography.labelSmall.copy(color = LightPrimary),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

            }
        }
    }
}


@Preview
@Composable
private fun NewsItemViewPreview() {
    GoodFeelingTheme {
        NewsItemView(
            NewsItemEntity(
                cover = R.drawable.hormoz,
                title = "بازار خودرو تهران",
                description = "از 700 کیلومتر دورتر آمده بود و سودای خرید اتومبیل شخصی داشت. به یکی از مراکز خرید و فروش خودرو در تهران رفته بود و با فرض اینکه یکی از بهترین ماشین\u200Cهای بازار خودرو را پیدا کرده برای عقد قرارداد و نوشتن قولنامه دست به جیب شده بود و بیعانه سنگینی پرداخت کرده بود غافل از آنکه طرف حسابش دلالی خبره یا بهتر بگوییم کلاهبرداری با سابقه است.",
                link = "link"
            )
        )
    }
}