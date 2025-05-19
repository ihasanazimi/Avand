package ir.hasanazimi.avand.presentation.screens.home

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.date_time.DateUtils
import ir.hasanazimi.avand.common.date_time.PersianCalendar1
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.withNotNull
import ir.hasanazimi.avand.common.security_and_permissions.askPermission
import ir.hasanazimi.avand.common.security_and_permissions.isPermissionGranted
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.calander.CalendarEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.presentation.dialogs.WebViewDialog
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.screens.news.NewsScreen
import ir.hasanazimi.avand.presentation.screens.web_view.WebViewScreen
import ir.hasanazimi.avand.presentation.screens.widgets.WidgetsScreen
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(activity: MainActivity, navController: NavHostController) {

    val TAG = "HomeScreen"
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenVM>()

    var weatherResponseState = viewModel.weatherResponse.collectAsStateWithLifecycle()
    var tempOfCalendar = viewModel.tempOfCalendar.collectAsStateWithLifecycle()
    var urlFromNewsScreen by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {

        activity.locationAccessFinePermissionsResult.collect {
            Log.i(TAG, "HomeScreen: permission result is ${activity.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)} ")
            if (activity.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                viewModel.prepareWeatherData(activity)
            } else {
                activity.askPermission(
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    requestCode = 1001,
                    onPermissionAlreadyGranted = {
                        viewModel.getLastLocation(activity, viewModel)
                    },
                    onShowRationale = {
                        showToast(context,"برای دریافت موقعیت ممکانی شما نیاز به مجوز داریم")
                    },
                    onRequest = { viewModel.prepareWeatherData(activity) }
                )
            }
        }


    }




    if (urlFromNewsScreen.isNotEmpty()){
        WebViewDialog(
            onDismissRequest = { urlFromNewsScreen = "" },
            content = {
                WebViewScreen(newsUrl = urlFromNewsScreen) { urlFromNewsScreen = "" }
            }
        )
    }


     HomeScreenContent(
         viewModel = viewModel,
         activity = activity,
         navController = navController,
         tempOfCalendar = tempOfCalendar,
         weatherResponseState = weatherResponseState,
    ){ newUrl ->
        urlFromNewsScreen = newUrl
    }


}

@Composable
private fun HomeScreenContent(
    viewModel: HomeScreenVM?,
    activity: MainActivity,
    navController: NavHostController,
    tempOfCalendar: State<Pair<String, Triple<Int, Int, Int>>?>,
    weatherResponseState: State<ResponseState<WeatherEntity>?>,
    urlFromNewsScreenCallBack :(newUrl : String) -> Unit
) {
    AvandTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    weatherResponseState.value.withNotNull { weatherData ->
                        WidgetsScreen(
                            activity = activity,
                            weatherData = weatherData,
                            calendarData = CalendarEntity(
                                dayOfWeek = PersianCalendar1().strWeekDay,
                                globalDate = "${tempOfCalendar.value?.second?.first ?: 0.toInt()}  ${
                                    DateUtils.getGregorianMonthNameInPersian(
                                        tempOfCalendar.value?.second?.second?.toInt() ?: 0
                                    )
                                }  ${tempOfCalendar.value?.second?.third ?: 0.toInt()}",
                                persianDate = tempOfCalendar.value?.first ?: "",
                                events = viewModel?.calendarResponse?.value ?: emptyList()
                            ),
                            onPrepareWeatherData = {
                                viewModel?.prepareWeatherData(activity)
                            }
                        )
                    }
                }
                item {
                    NewsScreen(
                        activity = activity,
                        navController = navController,
                        showMoreBtn = true ,
                        showMoreNewsCallBack = {
                            navController.navigate(Screens.AllNews.routeId)
                        },
                        openWebView = { url -> urlFromNewsScreenCallBack.invoke(url) }
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AvandTheme {

        val nav = rememberNavController()
        var weatherResponse = MutableStateFlow<ResponseState<WeatherEntity>?>(null)
        val tempOfCalendar = MutableStateFlow<Pair<String, Triple<Int, Int, Int>>?>(null)

        HomeScreenContent(
            viewModel = null,
            activity = MainActivity(),
            navController = nav,
            tempOfCalendar = tempOfCalendar.collectAsState(),
            weatherResponseState = weatherResponse.collectAsState()
        ){ newUrl ->

        }
    }
}