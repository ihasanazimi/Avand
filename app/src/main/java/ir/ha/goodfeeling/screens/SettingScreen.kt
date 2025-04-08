package ir.ha.goodfeeling.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.screens.itemViews.SettingItemView
import ir.ha.goodfeeling.screens.itemViews.settingItems
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.RedColor
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue


@Composable
fun SettingScreen(modifier: Modifier = Modifier, navController: NavController) {
    Surface {

        val userName by remember { mutableStateOf("حسن عظیمی") }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                MyLottieAnimation(
                    Modifier
                        .height(134.dp)
                        .width(230.dp)
                        .weight(1f)
                        .graphicsLayer(rotationZ = 180f)
                        .graphicsLayer(rotationX = 180f)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "سـلام",
                        style = CustomTypography.labelSmall,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = userName,
                        style = CustomTypography.titleLarge,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }


            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .height(80.dp)
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    border = BorderStroke(
                        2.dp,
                        TransparentlyBlue
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        var checked by remember { mutableStateOf(true) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                Modifier.weight(0.6f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "اعلانها فعال باشن؟",
                                    modifier = Modifier.fillMaxWidth(),
                                    style = CustomTypography.labelSmall.copy(
                                        textAlign = TextAlign.Start,
                                    ),
                                    lineHeight = TextUnit(20f, TextUnitType.Sp)
                                )
                                Text(
                                    text = if (checked) "بله" else "خیر",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    style = CustomTypography.labelSmall.copy(
                                        textAlign = TextAlign.Start,
                                    ),
                                    color = if (checked) LightPrimary else RedColor,
                                    lineHeight = TextUnit(20f, TextUnitType.Sp)
                                )
                            }


                            Checkbox(
                                colors = CheckboxDefaults.colors(checkedColor = LightPrimary),
                                checked = checked,
                                onCheckedChange = { checked = it }
                            )
                        }

                    }
                }



                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    border = BorderStroke(
                        2.dp,
                        TransparentlyBlue
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                        .clickable {
                            // todo
                        },
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.6f)
                            ) {

                                Text(
                                    text = "لوکیشن انتخابی",
                                    modifier = Modifier.fillMaxWidth(),
                                    style = CustomTypography.labelSmall.copy(
                                        textAlign = TextAlign.Start,
                                    ),
                                    lineHeight = TextUnit(20f, TextUnitType.Sp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "تهران",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        color = LightPrimary
                                    )
                                }


                            }


                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "location",
                                modifier = Modifier.weight(0.2f)
                            )

                        }

                    }
                }

            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                LazyColumn {
                    items(settingItems) {
                        SettingItemView(it)
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        SettingScreen(Modifier, navController)
    }
}