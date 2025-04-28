package ir.hasanazimi.avand.presentation.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.navigation.BottomNavigationBar
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun HostScreen(activity : MainActivity , navController: NavHostController) {

    val viewModel = hiltViewModel<HostScreenVM>()
    var userNameState by remember { mutableStateOf("") }
    val hostNavController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    SideEffect {
        coroutineScope.launch {
            viewModel.userName.collect {
                userNameState = it
            }
        }
    }

    AvandTheme {

        Scaffold(
            modifier = Modifier,
            bottomBar = { BottomNavigationBar(navController = hostNavController) },
            topBar = { TopBar(userNameState) }
        ) { innerPadding ->
            Content(
                innerPadding = innerPadding,
                hostNavController = hostNavController,
                mainNavController = navController,
                activity = activity
            )
        }

    }
}

@Composable
private fun Content(
    innerPadding: PaddingValues,
    hostNavController: NavHostController,
    mainNavController: NavHostController,
    activity: MainActivity
) {
    val TAG = "ContentTag"

    BackHandler(
    /*enabled = hostNavController.currentBackStackEntry?.destination?.route != Screens.Home.routeId*/)
    {
        if (hostNavController.currentBackStackEntry?.destination?.route == Screens.Home.routeId) {
            activity.finish()
        } else {
            hostNavController.popBackStack(Screens.Home.routeId, inclusive = true)
        }
    }

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = hostNavController,
                startDestination = Screens.Home.routeId,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screens.Home.routeId) {
                    HomeScreen(activity = activity, navController = mainNavController)
                }

                composable(route = Screens.CurrencyPrices.routeId) {
                    CurrencyPricesScreen(activity = activity, navController = mainNavController)
                }

                composable(route = Screens.Setting.routeId) {
                    SettingScreen(activity = activity, navController = mainNavController)
                }
            }
        }
    }
}




@Composable
fun TopBar(userName: String) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                MyLottieAnimation(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .height(124.dp)
                        .width(230.dp)
                        .weight(1f)
                        .graphicsLayer(rotationZ = 180f)
                        .graphicsLayer(rotationX = 180f)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "سـلام",
                        style = CustomTypography.labelSmall.copy(color = MaterialTheme.colorScheme.secondary),
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
        }
    }
}


@Composable
fun MyLottieAnimation(modifier: Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.birds)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

@HiltViewModel
class HostScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel(){

    val TAG = "HostScreenVM"

    init {
        getUserName()
    }

    val userName = MutableSharedFlow<String>()

    fun getUserName(){
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect {
                Log.i(TAG, "getUserName: $it")
                userName.emit(it?:"")
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    AvandTheme {
        HostScreen(activity = MainActivity() , navController = rememberNavController())
    }
}