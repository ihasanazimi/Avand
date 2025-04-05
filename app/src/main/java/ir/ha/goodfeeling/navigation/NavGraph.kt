package ir.ha.goodfeeling.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.ha.goodfeeling.screens.HomeScreen
import ir.ha.goodfeeling.screens.HostScreen
import ir.ha.goodfeeling.screens.IntroScreen
import ir.ha.goodfeeling.screens.NameRegisterScreen
import ir.ha.goodfeeling.screens.SchedulingScreen
import ir.ha.goodfeeling.screens.SettingScreen


@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Intro.route) {

        composable(Screens.Intro.route) {
            IntroScreen(navController)
        }

        composable(Screens.NameRegister.route) {
            NameRegisterScreen(navController)
        }

        composable(Screens.Scheduling.route) {
            SchedulingScreen(navController)
        }

        composable(Screens.Host.route) {
            HostScreen()
        }

        composable(Screens.Home.route) {
            HomeScreen(modifier = Modifier, navController)
        }

        composable(Screens.Setting.route) {
            SettingScreen(modifier = Modifier, navController)
        }
    }
}

