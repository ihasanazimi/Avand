package ir.hasanazimi.avand.presentation.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.common.date_time.CalendarManager
import ir.hasanazimi.avand.common.date_time.DateUtils
import ir.hasanazimi.avand.common.date_time.DateUtils2
import ir.hasanazimi.avand.common.date_time.PersianCalendar1
import ir.hasanazimi.avand.common.date_time.RoozhDateConverter
import ir.hasanazimi.avand.common.date_time.getEvents
import ir.hasanazimi.avand.common.extensions.isLocationEnabled
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.turnOnGPS
import ir.hasanazimi.avand.common.more.LocationHelper
import ir.hasanazimi.avand.common.security_and_permissions.askPermission
import ir.hasanazimi.avand.common.security_and_permissions.isPermissionGranted
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.calander.CalendarEntity
import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity
import ir.hasanazimi.avand.data.entities.local.weather.WeatherEntity
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.dialogs.Wide70PercentHeightDialog
import ir.hasanazimi.avand.presentation.screens.news.NewsScreen
import ir.hasanazimi.avand.presentation.screens.widgets.Widgets
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import ir.hasanazimi.avand.use_cases.NewsRssUseCase
import ir.hasanazimi.avand.use_cases.WeatherUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

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
                    getLastLocation(activity, viewModel)
                } else {
                    activity.askPermission(
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                        requestCode = 1001,
                        onPermissionAlreadyGranted = {
                            getLastLocation(activity, viewModel)
                        },
                        onShowRationale = {
                            Toast.makeText(
                                context,
                                "برای دریافت موقعیت ممکانی شما نیاز به مجوز داریم",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onRequest = {
                            getLastLocation(activity, viewModel)
                        }
                    )
                }
            } else {
                Toast.makeText(
                    context,
                    "اجازه دسترسی به سرویس مکان توسط شما صادر نشده است!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    AvandTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
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
                        },
                        onRefresh = {

                        }
                    )
                }
            }
        }



        if (newsUrl.isNotEmpty()){
            Wide70PercentHeightDialog(
                onDismissRequest = { newsUrl = "" },
                content = {
                    WebViewScreen(url = newsUrl) {
                        newsUrl = ""
                    }
                }
            )
        }

    }
}



@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    onBackPressed: (() -> Unit)? = null
) {

    if (url.isNotEmpty()) {

        val context = LocalContext.current
        val loading = remember { mutableStateOf(true) }

        val webView = remember {
            WebView(context).apply {

                settings.javaScriptEnabled = true
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(
                        view: WebView?,
                        url: String?,
                        favicon: Bitmap?
                    ) {
                        loading.value = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        loading.value = false
                    }
                }
                loadUrl(url)
            }
        }
        BackHandler(enabled = true) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                onBackPressed?.invoke()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {

            Box(modifier = Modifier.weight(0.9f)) {
                AndroidView(factory = { webView }, modifier = Modifier.fillMaxSize())
                if (loading.value) {
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
                            .background(Color.Black.copy(alpha = 0.8f)).clickable{}.focusable(true),
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.loading),
                                contentDescription = "loading image",
                                modifier = Modifier
                                    .size(32.dp)
                                    .graphicsLayer {
                                        rotationZ = rotation
                                    },
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }

                        Text(
                            text = "کمی صبر کنید..",
                            textAlign = TextAlign.Center,
                            style = CustomTypography.labelLarge.copy(color = Color.White),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onSecondary)
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.08f)
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp), onClick = {
                        onBackPressed?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "بستن",
                        style = CustomTypography.bodyLarge.copy(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}


/*useDarkIcons = isSystemInDarkTheme()
 SetStatusBarColor(defaultColor, useDarkIcons)

 DisposableEffect(Unit) {
     onDispose {
         defaultColor = Color.Transparent
         systemUiController.setStatusBarColor(defaultColor,useDarkIcons.not())
     }
 }*/

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean = true) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}






private fun getLastLocation(
    activity: MainActivity,
    viewModel: HomeScreenVM
) {

    if (isLocationEnabled(activity).not()) {
        turnOnGPS(activity)
        showToast(activity, "لطفا سرویس مکان را روشن کنید")
    } else {
        viewModel.weatherResponse.value = ResponseState.Loading
        LocationHelper(activity).getLastLocation(
            onSuccess = {
                val latLng = it.latitude.toString() + "," + it.longitude.toString()
                viewModel.getCurrentWeatherFromRemote(latLng).also {
                    Log.d(
                        "TAG",
                        "LocationHelper(context).getLastLocation - onSuccess by $latLng "
                    )
                }
            },
            onFailure = {
                Log.e(
                    "TAG",
                    "LocationHelper(context).getLastLocation - onFailure by ${it.message} "
                )
                Toast.makeText(
                    activity,
                    "خطا در دریافت موقعیت مکانی",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.weatherResponse.value =
                    ResponseState.Error(Exception("خطا در دریافت موقعیت مکانی"))
            }
        )
    }
}


@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val newsRssUseCase: NewsRssUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val TAG = "HomeScreenVM"

    val errorMessage = MutableSharedFlow<String>()

    var weatherResponse = MutableStateFlow<ResponseState<WeatherEntity>?>(null)
    var calendarResponse = MutableStateFlow<List<EventOfDayEntity>?>(null)

    fun getCurrentWeatherFromRemote(q: String) {
        Log.i(TAG, "getCurrentWeatherFromRemote called")
        viewModelScope.launch {
            weatherUseCase.getCurrentWeather(q).collectLatest { result ->
                Log.i(TAG, "getCurrentWeatherFromRemote: $result ")
                weatherResponse.emit(result)
            }
        }
    }

    fun getCurrentWeatherFromLocal(q: String = "Tehran") {
        Log.i(TAG, "getCurrentWeatherFromLocal called")
        viewModelScope.launch {
            dataStoreManager.automaticWeatherDataFlow.collect { cash ->
                /** IF cache was empty then get data from remote */
                if (cash == null) {
                    getCurrentWeatherFromRemote(q)
                } else {
                    try {
                        Log.i(TAG, "getCurrentWeatherFromLocal: local weather is : $cash")
                        val w = Gson().fromJson<WeatherEntity>(
                            cash,
                            WeatherEntity::class.java
                        )
                        weatherResponse.emit(ResponseState.Success(w))
                    } catch (e: IOException) {
                        getCurrentWeatherFromRemote(q)
                        Log.i(TAG, "getCurrentWeatherFromLocal: error is ${e.message}")
                    }
                }
            }
        }
    }






    fun getEvents(context: Context){
        viewModelScope.launch {
            val calendarManager = CalendarManager(context)
            calendarManager.loadCalendarData("taghvim.json")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val obj = calendarManager.getDayInfo(
                    shamsiDate = PersianCalendar1.shamsiDate(),
                    gregorianDate = DateUtils2.getTodayDate().gregorianDate,
                )
                calendarResponse.emit(obj?.getEvents())
                Log.i(TAG, "getEvents: $obj")
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AvandTheme {
        HomeScreen(
            activity = MainActivity(),
            navController = rememberNavController()
        )
    }
}