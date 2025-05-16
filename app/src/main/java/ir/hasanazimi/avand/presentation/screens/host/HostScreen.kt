package ir.hasanazimi.avand.presentation.screens.host

import android.os.Bundle
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.navigation.BottomNavigationBar
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.screens.currency_prices.CurrencyPricesScreen
import ir.hasanazimi.avand.presentation.screens.home.HomeScreen
import ir.hasanazimi.avand.presentation.screens.setting.SettingScreen
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun HostScreen(activity: MainActivity, navController: NavHostController) {

    val TAG = "HostScreen"
    val viewModel = hiltViewModel<HostScreenVM>()
    var userNameState = viewModel.userName.collectAsStateWithLifecycle("کاربر بدون نام")
    val lastDestination = viewModel.lastDestination.collectAsState(Screens.Home.routeId)

    val hostNavController = rememberNavController()

    AvandTheme {
        Scaffold(
            modifier = Modifier,
            bottomBar = { BottomNavigationBar(navController = hostNavController , lastDestination) },
            topBar = { TopBar(userNameState.value) }
        ) { innerPadding ->

            BackHandler(/*enabled = hostNavController.currentBackStackEntry?.destination?.route != Screens.Home.routeId*/) {
                if (hostNavController.currentBackStackEntry?.destination?.route == Screens.Home.routeId) {
                    activity.finish()
                } else {
                    hostNavController.popBackStack(Screens.Home.routeId, inclusive = true)
                }
            }


            LaunchedEffect(hostNavController) {
                val obj : NavController.OnDestinationChangedListener = object : NavController.OnDestinationChangedListener{
                    override fun onDestinationChanged(
                        controller: NavController,
                        destination: NavDestination,
                        arguments: Bundle?
                    ) {
                        Log.i(TAG, "onDestinationChanged: ${destination.route}")
                        viewModel.lastDestination.value = destination.route?: Screens.Home.routeId
                    }
                }
                hostNavController.addOnDestinationChangedListener(obj)
            }


            HostScreenContent(
                innerPadding = innerPadding,
                hostNavController = hostNavController,
                mainNavController = navController,
                activity = activity
            )
        }
    }
}

@Composable
private fun HostScreenContent(
    activity: MainActivity,
    innerPadding: PaddingValues,
    hostNavController: NavHostController,
    mainNavController: NavHostController
) {

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
                        maxLines = 2,
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


@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    AvandTheme {
        val activity = MainActivity()
        val hostNavController = rememberNavController()
        val mainNavController = rememberNavController()
        HostScreenContent(
            activity = activity,
            innerPadding = PaddingValues(16.dp),
            hostNavController = hostNavController,
            mainNavController = mainNavController,
        )
    }
}