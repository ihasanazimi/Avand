package ir.hasanazimi.avand.presentation.screens

import android.content.Context
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.extensions.withNotNull
import ir.hasanazimi.avand.common.file.AssetHelper
import ir.hasanazimi.avand.data.entities.local.other.CityEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.bottom_sheets.CitiesModalBottomSheet
import ir.hasanazimi.avand.presentation.bottom_sheets.UserProfileBottomSheet
import ir.hasanazimi.avand.presentation.itemViews.SettingItemView
import ir.hasanazimi.avand.presentation.itemViews.settingItems
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import ir.hasanazimi.avand.presentation.theme.RedColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@Composable
fun SettingScreen(activity: MainActivity, navController: NavController) {

    val viewModel = hiltViewModel<SettingScreenVM>()
    val coroutineScope = rememberCoroutineScope()

    var citiesModalOpenState by remember { mutableStateOf(false) }
    var userProfileModalOpenState by remember { mutableStateOf(false) }

    var userNameState by remember { mutableStateOf("") }
    var defaultCityState by remember { mutableStateOf<CityEntity?>(viewModel.defaultCity.value) }


    Surface {

        SideEffect {

            viewModel.getDefaultCity()
            viewModel.getUserName()

            coroutineScope.launch {
                viewModel.userName.collect { userName ->
                    userNameState = userName
                }
            }

            coroutineScope.launch {
                viewModel.defaultCity.collect {
                    defaultCityState = it
                }
            }
        }


        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 14.dp, end = 14.dp, bottom = 8.dp, top = 8.dp)
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
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
                                    color = if (notificationCheckedToggle) MaterialTheme.colorScheme.primary else RedColor,
                                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                                    maxLines = 1
                                )
                            }


                            Checkbox(
                                checked = notificationCheckedToggle,
                                onCheckedChange = { notificationCheckedToggle = it }
                            )
                        }

                    }
                }



                Card(
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(start = 4.dp)
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
                                        text = defaultCityState?.cityName ?: "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        color = MaterialTheme.colorScheme.primary,
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
                                    viewModel.getUserName().also {
                                        userProfileModalOpenState = true
                                    }
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


    CitiesModalBottomSheet(
        isOpen = citiesModalOpenState,
        selectedCity = defaultCityState,
        citiesSnapshotList = viewModel.prepareCities(),
        onDismiss = { city ->
            defaultCityState = city
            citiesModalOpenState = false
        }
    ) { city ->
        defaultCityState = city
        defaultCityState.withNotNull { viewModel.saveDefaultCity(it) }
        citiesModalOpenState = false
    }


    UserProfileBottomSheet(lastUserName = userNameState, isOpen = userProfileModalOpenState) {
        userNameState = it
        viewModel.saveUserName(userNameState).also {
            userProfileModalOpenState = false
        }
    }

}


@HiltViewModel
class SettingScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val context: Context
) : ViewModel() {

    val TAG = "SettingScreenVM"

    val userName = MutableStateFlow("")
    val defaultCity = MutableStateFlow<CityEntity?>(null)
    var snapShotCities = mutableStateListOf<CityEntity>()

    fun saveUserName(newName: String) {
        viewModelScope.launch {
            dataStoreManager.saveUserName(newName)
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect {
                Log.i(TAG, "getUserName: $it")
                userName.emit(it ?: "کاربر بدون نام")
            }
        }
    }


    private fun readCitiesFromJson() : List<CityEntity>{
        var localCities = emptyList<CityEntity>()
        val jsonText = AssetHelper.readJsonFromAssets(context, "cities.json")
        val typeToken = object : TypeToken<List<CityEntity>>() {}.type
        runCatching {
            localCities = Gson().fromJson<List<CityEntity>>(jsonText, typeToken)
            localCities = localCities.sortedBy { it.cityName }
        }.onFailure {
            Log.i(TAG, "prepareLocalCities: ${it.message}")
        }
        return localCities
    }

    fun prepareCities() : SnapshotStateList<CityEntity>{
        var tempOfCities = readCitiesFromJson()
        val selectedCity = tempOfCities.find { it.cityName == defaultCity.value?.cityName }
        val toc = tempOfCities.map {
            if (it.cityName == selectedCity?.cityName){
                it.copy(selected = true)
            }else{
                it.copy(selected = false)
            }
        }
        snapShotCities.clear()
        snapShotCities.addAll(toc)
        return snapShotCities
    }




    fun saveManualWeatherData(weatherEntity: WeatherEntity) {
        viewModelScope.launch {
            val json = Gson().toJson(weatherEntity)
            dataStoreManager.saveManualWeatherData(json)
        }
    }
    fun getManualWeatherData(){
        viewModelScope.launch {
            dataStoreManager.manualWeatherData.collect {
                // todo
            }
        }
    }
    fun removeManualWeatherData() {
        viewModelScope.launch {
            dataStoreManager.removeManualWeatherData()
        }
    }




    fun saveDefaultCity(cityEntity: CityEntity){
        viewModelScope.launch {
            val json = Gson().toJson(cityEntity)
            dataStoreManager.saveDefaultCity(json)
        }
    }
    fun getDefaultCity(){
        viewModelScope.launch {
            dataStoreManager.defaultCity.collect { cityJson ->
                try{
                    val type = object : TypeToken<CityEntity>(){}.type
                    val city = Gson().fromJson<CityEntity>(cityJson,type)
                    defaultCity.emit(city)
                    Log.i(TAG, "getDefaultCity: $city")
                }catch (e : IOException){
                    Log.d(TAG, "getDefaultCity: ${e.message}")
                }
            }
        }
    }
    fun removeDefaultCity(){
        viewModelScope.launch {
            dataStoreManager.removeDefaultCity()
        }
    }





    fun getManualWeather() {
        viewModelScope.launch {
            dataStoreManager.manualWeatherData.collect { location ->
                
            }
        }
    }




}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    AvandTheme {
        SettingScreen(
            activity = MainActivity(),
            navController = rememberNavController()
        )
    }
}