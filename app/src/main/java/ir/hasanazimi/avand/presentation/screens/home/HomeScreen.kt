package ir.hasanazimi.avand.presentation.screens.home

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import ir.hasanazimi.avand.common.date_time.RoozhDateConverter
import ir.hasanazimi.avand.common.security_and_permissions.askPermission
import ir.hasanazimi.avand.common.security_and_permissions.isPermissionGranted
import ir.hasanazimi.avand.data.entities.local.calander.CalendarEntity
import ir.hasanazimi.avand.presentation.dialogs.WebViewDialog
import ir.hasanazimi.avand.presentation.screens.news.NewsScreen
import ir.hasanazimi.avand.presentation.screens.widgets.Widgets
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.screens.web_view.WebViewScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(activity: MainActivity, navController: NavHostController) {

    val TAG = "HomeScreen"
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenVM>()
    val coroutineScope = rememberCoroutineScope()


    var weatherResponseState = viewModel.weatherResponse.collectAsStateWithLifecycle()
    var errorMessageState = viewModel.errorMessage.collectAsStateWithLifecycle("")
    var newsUrl by remember { mutableStateOf("") }


    val dateArray = PersianCalendar1.shamsiDate().split("/")
    val persianDate = "${dateArray[2].toInt()} " + PersianCalendar1().strMonth + " ${dateArray[0].toInt()}"
    val roozhDate = RoozhDateConverter()
    roozhDate.persianToGregorian(dateArray[0].toInt(), dateArray[1].toInt(), dateArray[2].toInt())
    val y = roozhDate.year
    val m = roozhDate.month
    val d = roozhDate.day



    LaunchedEffect(Unit) {

        viewModel.getCurrentWeatherFromLocal()
        viewModel.getEvents(context)

        if (errorMessageState.value.isNotEmpty()){
            Toast.makeText(context, errorMessageState.value, Toast.LENGTH_SHORT).show()
        }

        activity.locationAccessFinePermissionsResult.collect { result ->
            Log.i(TAG, "HomeScreen: permission result is $result ")

            if (result) {
                if (activity.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    viewModel.getLastLocation(activity, viewModel)
                } else {
                    activity.askPermission(
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                        requestCode = 1001,
                        onPermissionAlreadyGranted = {
                            viewModel.getLastLocation(activity, viewModel)
                        },
                        onShowRationale = {
                            viewModel.errorMessage.tryEmit("برای دریافت موقعیت ممکانی شما نیاز به مجوز داریم")
                        },
                        onRequest = { viewModel.getLastLocation(activity, viewModel) }
                    )
                }
            } else {
               viewModel.errorMessage.tryEmit("اجازه دسترسی به سرویس مکان توسط شما صادر نشده است!")
            }
        }
    }



    AvandTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    Widgets(
                        activity = activity,
                        weatherData = weatherResponseState.value,
                        calendarData = CalendarEntity(
                            dayOfWeek = PersianCalendar1().strWeekDay,
                            globalDate = "${d.toInt()}  ${DateUtils.getGregorianMonthNameInPersian(m.toInt())}  ${y.toInt()}",
                            persianDate = persianDate,
                            events = viewModel.calendarResponse.value ?: emptyList()
                        ),
                        onGetData = {
                            coroutineScope.launch {
                                activity.locationAccessFinePermissionsResult.emit(true)
                            }
                        }
                    )
                }
                item {
                    NewsScreen(
                        activity = activity,
                        navController = navController,
                        openWebView = { url ->
                            newsUrl = url
                        }
                    )
                }
            }
        }

        if (newsUrl.isNotEmpty()){
            WebViewDialog(
                onDismissRequest = { newsUrl = "" },
                content = {
                    WebViewScreen(url = newsUrl) { newsUrl = "" }
                }
            )
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AvandTheme {
        val nav = rememberNavController()
        HomeScreen(
            activity = MainActivity(),
            navController = nav
        )
    }
}