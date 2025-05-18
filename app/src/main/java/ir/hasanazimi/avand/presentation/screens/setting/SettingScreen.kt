package ir.hasanazimi.avand.presentation.screens.setting

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.extensions.withNotNull
import ir.hasanazimi.avand.presentation.bottom_sheets.CitiesModalBottomSheet
import ir.hasanazimi.avand.presentation.bottom_sheets.UserProfileBottomSheet
import ir.hasanazimi.avand.presentation.itemViews.CustomSpacer
import ir.hasanazimi.avand.presentation.itemViews.SettingItemView
import ir.hasanazimi.avand.presentation.itemViews.settingItems
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import ir.hasanazimi.avand.presentation.theme.RedColor


@Composable
fun SettingScreen(activity: MainActivity, navController: NavController) {

    val viewModel = hiltViewModel<SettingScreenVM>()

    var citiesModalOpenState by remember { mutableStateOf(false) }
    var profileModalOpenState by remember { mutableStateOf(false) }

    var userNameState = viewModel.userName.collectAsStateWithLifecycle()
    var defaultCityState = viewModel.defaultCity.collectAsStateWithLifecycle()
    var notificationCheckedToggle by remember { mutableStateOf(false) }

    SettingContent(
        navController = navController,
        defaultCityState = defaultCityState.value?.cityName ?: "",
        notificationChecked = notificationCheckedToggle,
        citiesModalOpenCallBack = { citiesModalOpenState = it },
        profileModalOpenCallBack = { profileModalOpenState = it },
        notificationCheckedToggle = { notificationCheckedToggle = it }
    )

    CitiesModalBottomSheet(
        isOpen = citiesModalOpenState,
        selectedCity = defaultCityState.value,
        citiesSnapshotList = viewModel.prepareCities(),
        onDismiss = { city ->
            viewModel.saveAndNotifyDefaultCity(city)
            citiesModalOpenState = false
        }
    ) { city ->
        citiesModalOpenState = false
        city.withNotNull { it -> viewModel.saveAndNotifyDefaultCity(it) }
    }


    UserProfileBottomSheet(lastUserName = userNameState.value, isOpen = profileModalOpenState) { newName ->
        viewModel.saveAndNotifyUserName(newName)
        profileModalOpenState = false
    }


}


@Composable
private fun SettingContent(
    navController: NavController,
    defaultCityState: String?,
    notificationChecked: Boolean,
    citiesModalOpenCallBack: (open: Boolean) -> Unit,
    profileModalOpenCallBack: (open: Boolean) -> Unit,
    notificationCheckedToggle: (checked : Boolean) -> Unit

) {
    AvandTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .height(62.dp),
                    onClick = {
                        notificationCheckedToggle.invoke(notificationChecked.not())
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxSize(),
                    ) {

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column {
                                Text(
                                    modifier = Modifier.align(Alignment.End),
                                    text = "نوتیفیکیشن",
                                    style = CustomTypography.bodyLarge.copy(textAlign = TextAlign.Start),
                                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                                    maxLines = 1
                                )
                                Text(
                                    text = "اعلان های اپلیکیشن فعال باشه؟",
                                    style = CustomTypography.labelSmall.copy(textAlign = TextAlign.Start),
                                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                                    maxLines = 1
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(22.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Row(modifier = Modifier.fillMaxHeight()) {

                            Checkbox(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                checked = notificationChecked,
                                onCheckedChange = { notificationCheckedToggle.invoke(it) }
                            )

                            Text(
                                text = if (notificationChecked) "فعاله" else "غیر فعاله",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = CustomTypography.labelSmall.copy(
                                    textAlign = TextAlign.Start,
                                ),
                                color = if (notificationChecked) MaterialTheme.colorScheme.primary else RedColor,
                                maxLines = 1
                            )
                        }

                    }
                }

                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 14.dp)
                        .height(62.dp),
                    onClick = {
                        citiesModalOpenCallBack.invoke(true)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxSize(),
                    ) {

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "شهر محل سکونت",
                                style = CustomTypography.bodyLarge.copy(textAlign = TextAlign.Start),
                                lineHeight = TextUnit(20f, TextUnitType.Sp),
                                maxLines = 1
                            )

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "location",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(22.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Row(modifier = Modifier.align(Alignment.CenterStart)) {

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "account",
                                modifier = Modifier
                                    .size(24.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )

                            Text(
                                text = defaultCityState ?: "انتخاب",
                                modifier = Modifier.padding(start = 8.dp),
                                style = CustomTypography.bodyLarge.copy(
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                lineHeight = TextUnit(20f, TextUnitType.Sp),
                                maxLines = 1
                            )
                        }

                    }
                }

            }


            CustomSpacer(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp))


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
                                    profileModalOpenCallBack.invoke(true)
                                }

                                Screens.AboutUs -> {
                                    navController.navigate(Screens.AboutUs.routeId)
                                }

                                else -> {
                                    Log.i("TAG", "SettingScreen: ${type?.routeId}")
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
fun SettingScreenPreview() {
    AvandTheme {
        SettingContent(
            navController = rememberNavController(),
            defaultCityState = "انتخاب",
            notificationChecked = false,
            citiesModalOpenCallBack = {

            },
            profileModalOpenCallBack = {

            },
            notificationCheckedToggle = {

            }
        )
    }
}