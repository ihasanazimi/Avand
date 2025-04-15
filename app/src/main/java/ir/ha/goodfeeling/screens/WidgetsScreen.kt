package ir.ha.goodfeeling.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.data.fakeOccasionsOfTheDayList
import ir.ha.goodfeeling.screens.itemViews.OccasionItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlack

@Composable
fun Widgets() {

    val whetherIsLoading by remember { mutableStateOf(false) }
    val time by remember { mutableStateOf("14:05") }
    val persianDate by remember { mutableStateOf("سه شنبه 26 فروردین") }
    val globalDate by remember { mutableStateOf("15 آوریل 2025") }

    GoodFeelingTheme {
        Row(
            Modifier
                .height(240.dp)
        ) {

            /**  big rectangle -> Whether */
            Card(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = LightPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box {
                            Text(
                                text = "℃" + "32",
                                style = CustomTypography.titleLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterStart)
                                    .padding(horizontal = 4.dp),
                                textAlign = TextAlign.Left
                            )

                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "refresh weather",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        // todo
                                    }
                            )

                        }

                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "location",
                                modifier = Modifier.size(20.dp),
                            )
                            Text(
                                text = "تهران",
                                style = CustomTypography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                textAlign = TextAlign.End,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.cloudy),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.7f)
                                .padding(vertical = 14.dp)
                        )

                        Text(
                            text = "آخ که توی این هوا بستنی خیلی زیاد میچسبه ✌ ",
                            Modifier
                                .fillMaxSize()
                                .weight(0.4f)
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Right,
                            lineHeight = TextUnit(24f, TextUnitType.Sp),
                            style = CustomTypography.labelSmall,
                            maxLines = 2
                        )

                    }

                    if (whetherIsLoading) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .background(TransparentlyBlack)
                                .alpha(0f),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Image(
                                contentDescription = "loading image",
                                painter = painterResource(R.drawable.loading),
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )

                            Text(
                                text = "کمی صبر کنید..",
                                textAlign = TextAlign.Center,
                                style = CustomTypography.labelLarge,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }


            /** small rectangles */
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                /** Time and Date */
                Card(
                    modifier = Modifier
                        .padding(start = 4.dp, bottom = 4.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time,
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = CustomTypography.titleLarge
                            )
                        }

                        Column(modifier = Modifier.weight(0.6f)) {
                            Text(
                                text = persianDate,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = CustomTypography.bodyLarge
                            )


                            Text(
                                text = globalDate,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = CustomTypography.labelLarge
                            )
                        }

                    }
                }


                /** Occasions */
                Card(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                "مناسبت های امروز :",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                style = CustomTypography.labelLarge
                            )
                        }

                        Box(
                            modifier = Modifier.weight(0.7f),
                            contentAlignment = Alignment.Center
                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(1f)
                            ) {
                                Row(
                                    modifier = Modifier.align(Alignment.Center),
                                    verticalAlignment = Alignment.CenterVertically

                                ) {

                                    Text(
                                        text = "خبری نیست..",
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.nothing),
                                        contentDescription = "nothing",
                                        modifier = Modifier
                                            .size(18.dp),
                                    )

                                }
                            }

                            LazyColumn(
                                modifier = Modifier
                                    .alpha(0f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                items(fakeOccasionsOfTheDayList) { item ->
                                    OccasionItemView(occasionsOfTheDay = item)
                                }
                            }

                        }

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WidgetScreenPreview() {
    GoodFeelingTheme {
        Widgets()
    }
}