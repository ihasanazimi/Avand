package ir.ha.goodfeeling.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.data.getFakeCitiesList
import ir.ha.goodfeeling.data.entities.CityEntity
import ir.ha.goodfeeling.navigation.Screens
import ir.ha.goodfeeling.screens.bottom_sheets.CitiesModalBottomSheet
import ir.ha.goodfeeling.screens.bottom_sheets.UserProfileBottomSheet
import ir.ha.goodfeeling.screens.itemViews.SettingItemView
import ir.ha.goodfeeling.screens.itemViews.settingItems
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.RedColor
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue


@Composable
fun SettingScreen(navController: NavController) {

    Surface {

        val context = LocalContext.current
        var citiesModalOpenState by remember { mutableStateOf(false) }
        var userProfileModalOpenState by remember { mutableStateOf(false) }
        var userName by remember { mutableStateOf("Hasan Azimi") }
        var selectedCity by remember { mutableStateOf<CityEntity?>(getFakeCitiesList().find { it.selected }) }

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 12.dp, end = 12.dp, bottom = 4.dp, top = 8.dp)
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = TransparentlyBlue.copy(0.1f)),
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

                        var notificationCheckedToggle by remember { mutableStateOf(true) }

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
                                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                                    maxLines = 1
                                )
                                Text(
                                    text = if (notificationCheckedToggle) "بله" else "خیر",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    style = CustomTypography.labelSmall.copy(
                                        textAlign = TextAlign.Start,
                                    ),
                                    color = if (notificationCheckedToggle) LightPrimary else RedColor,
                                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                                    maxLines = 1
                                )
                            }


                            Checkbox(
                                colors = CheckboxDefaults.colors(checkedColor = LightPrimary),
                                checked = notificationCheckedToggle,
                                onCheckedChange = { notificationCheckedToggle = it }
                            )
                        }

                    }
                }



                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = TransparentlyBlue.copy(0.1f)),
                    border = BorderStroke(
                        2.dp,
                        TransparentlyBlue
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    onClick = {
                        citiesModalOpenState = true
                    }
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
                                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                                    maxLines = 1
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = selectedCity?.cityName ?: "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        color = LightPrimary,
                                        maxLines = 1
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
                        SettingItemView(it) { type ->
                            when (type) {
                                Screens.Setting -> {
                                    userProfileModalOpenState = true
                                }

                                Screens.AboutUs -> {
                                    navController.navigate(Screens.AboutUs.route)
                                }

                                else -> {
                                    Log.i("TAG", "SettingScreen: ${type?.route}")
                                }
                            }
                        }
                    }
                }
            }


            SideEffect {
                Log.i("TAG", "SettingScreen: ")
            }

            CitiesModalBottomSheet(
                citiesSnapshotList = getFakeCitiesList(),
                isOpen = citiesModalOpenState,
                selectedCity = selectedCity
            ) { returnedCity ->
                selectedCity = returnedCity
                citiesModalOpenState = false
            }


            UserProfileBottomSheet(lastUserName = userName, isOpen = userProfileModalOpenState) {
                userName = it
                userProfileModalOpenState = false
            }
            
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    GoodFeelingTheme {
        SettingScreen(rememberNavController())
    }
}