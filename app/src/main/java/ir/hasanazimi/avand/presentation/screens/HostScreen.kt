package ir.hasanazimi.avand.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
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
import ir.hasanazimi.avand.presentation.theme.getBackgroundColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun HostScreen(activity : MainActivity , navController: NavHostController) {

    val viewModel = hiltViewModel<HostScreenVM>()
    AvandTheme {

        var userNameState by remember { mutableStateOf(viewModel.userName.value) }
        val hostNavController = rememberNavController()


        SideEffect {
            viewModel.getUserName()
        }

        Scaffold(
            modifier = Modifier.background(color = getBackgroundColor()),
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
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = hostNavController,
                startDestination = Screens.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screens.Home.route) {
                    HomeScreen(activity = activity , navController = mainNavController)
                }

                composable(route = Screens.CurrencyPrices.route) {
                    CurrencyPricesScreen(activity = activity , navController = mainNavController)
                }

                composable(route = Screens.Setting.route) {
                    SettingScreen(activity = activity , navController = mainNavController)
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

    val userName = MutableStateFlow("")

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