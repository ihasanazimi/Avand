package ir.ha.goodfeeling.screens

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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlack

@Composable
fun Widgets(modifier : Modifier = Modifier){
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier
                .height(260.dp)
                .background(Color.Transparent)
        ) {

            /**  big rectangle -> Whether */
            Card(
                modifier = modifier
                    .padding(4.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                colors = CardColors(
                    containerColor = LightPrimary,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Box {
                    Column(
                        modifier = modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box {
                            Text(
                                text = "℃" + "32",
                                style = CustomTypography.titleLarge,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterStart)
                                    .padding(horizontal = 4.dp),
                                textAlign = TextAlign.Left
                            )

                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "refresh weather",
                                modifier = modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        // todo
                                    },
                                tint = Color.White
                            )

                        }

                        Row(
                            modifier = modifier.padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "location",
                                modifier = modifier.size(20.dp),
                                tint = Color.White
                            )
                            Text(
                                text = "Tehran",
                                style = CustomTypography.bodyLarge,
                                modifier = modifier.fillMaxWidth(),
                                maxLines = 1,
                                textAlign = TextAlign.End,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.cloudy2),
                            contentDescription = null,
                            modifier = modifier
                                .fillMaxWidth()
                                .weight(0.7f)
                                .alpha(0.9f)
                                .padding(vertical = 14.dp)
                        )

                        Text(
                            text = "آخ که توی این هوا بستنی میچسبه ✌ ",
                            modifier
                                .fillMaxSize()
                                .weight(0.4f)
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Right,
                            style = CustomTypography.labelLarge,
                            maxLines = 2
                        )

                    }

                    Box(
                        modifier
                            .fillMaxSize()
                            .background(TransparentlyBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            contentDescription = "loading image",
                            painter = painterResource(R.drawable.loading),
                            modifier = modifier,
                        )
                    }
                }
            }


            /** small rectangles */
            Column(
                modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                /** Time and Date */
                Card(
                    modifier = modifier
                        .padding(4.dp)
                        .weight(1f)
                        .fillMaxWidth(), colors = CardColors(
                        containerColor = LightPrimary,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = modifier
                                .weight(0.4f)
                                .fillMaxWidth()
                                .background(TransparentlyBlack),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "14:05",
                                modifier = modifier,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = CustomTypography.titleLarge
                            )
                        }

                        Column(modifier = modifier.weight(0.6f)) {
                            Text(
                                "یکشنبه 17 فروردین",
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = CustomTypography.bodyLarge
                            )


                            Text(
                                "6 آوریل 2025",
                                modifier = modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = CustomTypography.labelLarge
                            )
                        }

                    }
                }



                /** Occasions */
                Card(
                    modifier = modifier
                        .padding(4.dp)
                        .weight(1f)
                        .fillMaxWidth(), colors = CardColors(
                        containerColor = TransparentlyBlack,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = modifier
                                .weight(0.4f)
                                .fillMaxWidth()
                                .background(TransparentlyBlack),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                "مناسبت های امروز :",
                                modifier = modifier.padding(horizontal = 16.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                style = CustomTypography.labelLarge
                            )
                        }

                        LazyColumn(
                            modifier = modifier
                                .weight(0.7f)
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            items(occasionsOfTheDayList) { item ->
                                OccasionItemView(occasionsOfTheDay = item, modifier = modifier)
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
fun WidgetScreenPreview(){
    GoodFeelingTheme {
        Widgets()
    }
}