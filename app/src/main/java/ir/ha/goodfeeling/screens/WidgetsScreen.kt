package ir.ha.goodfeeling.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.data.fakeOccasionsOfTheDayList
import ir.ha.goodfeeling.data.models.enums.WeatherCondition
import ir.ha.goodfeeling.data.models.remote_response.weather.WeatherRemoteResponse
import ir.ha.goodfeeling.screens.itemViews.OccasionItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import kotlin.math.roundToInt

@Composable
fun Widgets(
    weatherLoading: Boolean = false,
    weatherData: WeatherRemoteResponse? = null,
    onRefresh: () -> Unit = {}
) {

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
                            .padding(start = 8.dp, end = 12.dp, bottom = 8.dp, top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box {
                            Text(
                                text = "℃" + "${weatherData?.current?.tempC?.roundToInt() ?: "?"}",
                                style = CustomTypography.titleLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterStart)
                                    .padding(horizontal = 8.dp),
                                textAlign = TextAlign.Left
                            )

                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "refresh weather",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(horizontal = 4.dp)
                                    .clickable {
                                        onRefresh.invoke()
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
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(horizontal = 4.dp),
                            )
                            Text(
                                text = weatherData?.location?.name ?: "",
                                style = CustomTypography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                textAlign = TextAlign.End,
                            )
                        }

                        /*AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(painterResource(WeatherCondition.fromCode(weatherData?.current?.condition?.code?: WeatherCondition.Unknown.code).iconResId))
                                .crossfade(true)
                                .build(),
                            contentDescription = "Loaded image",
                            modifier = Modifier
                                .size(120.dp)
                                .fillMaxWidth()
                                .weight(0.7f)
                                .clip(RoundedCornerShape(12.dp))
                        )*/

                        Image(
                            painter = painterResource(
                                WeatherCondition.fromCode(
                                    code = weatherData?.current?.condition?.code
                                        ?: WeatherCondition.Unknown.code,
                                    isDay = weatherData?.current?.isDay == 1 /* 1 is day and 0 is night*/
                                ).iconResId
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.6f)
                                .padding(vertical = 4.dp)
                        )

                        Text(
                            text = "uv : ${weatherData?.current?.uv?.roundToInt() ?: ""}" + "\n" + WeatherCondition.getUVSentences(
                                weatherData?.current?.uv?.roundToInt() ?: 0,
                                weatherData?.current?.isDay == 1
                            ),
                            Modifier
                                .fillMaxSize()
                                .weight(0.4f),
                            textAlign = TextAlign.Right,
                            lineHeight = TextUnit(24f, TextUnitType.Sp),
                            style = CustomTypography.labelSmall,
                            maxLines = 2
                        )

                        Text(
                            text = WeatherCondition.getSentences(
                                weatherData?.current?.feelslikeC?.roundToInt() ?: 0,
                                weatherData?.current?.isDay == 1
                            ),
                            Modifier
                                .fillMaxSize()
                                .weight(0.4f),
                            textAlign = TextAlign.Right,
                            style = CustomTypography.labelSmall,
                            maxLines = 2
                        )

                    }

                    if (weatherLoading) {
                        Loading()
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
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)),
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
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)),
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


@Composable
fun Loading() {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f)),
        verticalArrangement = Arrangement.Center,
    ) {

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.loading),
                contentDescription = "loading image",
                modifier = Modifier
                    .size(32.dp)
                    .graphicsLayer {
                        rotationZ = rotation
                    }
            )
        }

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


@Preview(showBackground = true)
@Composable
fun WidgetScreenPreview() {
    GoodFeelingTheme {
        Widgets()
    }
}