package ir.ha.goodfeeling.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.data.ResponseState
import ir.ha.goodfeeling.data.fakeOccasionsOfTheDayList
import ir.ha.goodfeeling.data.models.enums.WeatherCondition
import ir.ha.goodfeeling.data.models.local_entities.weather.WeatherEntity
import ir.ha.goodfeeling.screens.itemViews.OccasionItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.RedColor
import ir.ha.goodfeeling.ui.theme.TransparentlyWhite
import kotlin.math.roundToInt

@Composable
fun Widgets(
    weatherData: ResponseState<WeatherEntity>? = null,
    onGetData: () -> Unit = {}
) {

    val dayOfWeek by remember { mutableStateOf("سه شنبه") }
    val persianDate by remember { mutableStateOf("26 فروردین 1404") }
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
                    when (weatherData) {
                        is ResponseState.Success -> SuccessState(weatherData, onGetData)
                        is ResponseState.Loading -> LoadingState()
                        is ResponseState.Error -> ErrorState(onGetData)
                        else -> {}
                    }
                }
            }


            /** small rectangles */
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                /** dayOfWeek and persian and global Date */
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
                                text = dayOfWeek,
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
private fun SuccessState(
    weatherData: ResponseState.Success<WeatherEntity>,
    onRefresh: () -> Unit
) {

    val currentWeather = weatherData.data?.current
    val locationWeather = weatherData.data?.location

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp, end = 12.dp, bottom = 8.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Text(
                text = "℃ " + "${currentWeather?.tempC?.roundToInt() ?: ""}",
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
            modifier = Modifier.padding(top = 4.dp, start = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "location",
                modifier = Modifier
                    .size(18.dp),
            )
            Text(
                text = locationWeather?.name ?: "",
                style = CustomTypography.labelSmall,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                textAlign = TextAlign.End,
            )
        }

        /*AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    painterResource(
                        WeatherCondition.fromCode(
                            weatherData?.current?.condition?.code ?: WeatherCondition.Unknown.code
                        ).iconResId
                    )
                )
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
                    code = currentWeather?.condition?.code
                        ?: WeatherCondition.Unknown.code,
                    isDay = currentWeather?.isDay == 1 /* 1 is day and 0 is night*/
                ).iconResId
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .padding(vertical = 4.dp)
        )

        Text(
            text = "uv : ${currentWeather?.uv?.roundToInt() ?: ""}" + "\n" + WeatherCondition.getUVSentences(
                currentWeather?.uv?.roundToInt() ?: 0,
                currentWeather?.isDay == 1
            ) + "!",
            Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Right,
            lineHeight = TextUnit(16f, TextUnitType.Sp),
            style = CustomTypography.labelSmall,
            maxLines = 2
        )

        Spacer(
            Modifier
                .padding(vertical = 5.dp)
                .background(TransparentlyWhite)
                .fillMaxWidth()
                .height(1.dp)
        )

        Text(
            text = WeatherCondition.getSentences(
                currentWeather?.feelslikeC?.roundToInt() ?: 0,
                currentWeather?.isDay == 1
            ),
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Right,
            lineHeight = TextUnit(20f, TextUnitType.Sp),
            style = CustomTypography.labelSmall,
            maxLines = 2
        )

    }
}

@Composable
fun ErrorState(onRefresh: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f)),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {

            Image(
                painter = painterResource(R.drawable.baseline_error_outline_24),
                contentDescription = "error state",
                modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.CenterHorizontally),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Text(
                text = "خطا در برقراری ارتباط",
                textAlign = TextAlign.Center,
                style = CustomTypography.labelSmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp)
            )


            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onRefresh.invoke()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "تلاش مجدد",
                        textAlign = TextAlign.Center,
                        style = CustomTypography.labelSmall,
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 8.dp, top = 8.dp, end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "refresh weather",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp)
                    )
                }
            }
        }

    }
}


@Composable
fun LoadingState() {
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
        Widgets(
            weatherData = ResponseState.Error(Exception("sldlfk")),
            onGetData = {
                // todo
            }
        )
    }
}