package ir.ha.goodfeeling.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.ha.goodfeeling.screens.HomeScreen
import ir.ha.goodfeeling.screens.HostScreen
import ir.ha.goodfeeling.screens.IntroScreen
import ir.ha.goodfeeling.screens.NameRegisterScreen
import ir.ha.goodfeeling.screens.SchedulingScreen
import ir.ha.goodfeeling.screens.SettingScreen


@Composable
fun AppNavigator(navController: NavHostController, isIntroFinished: Boolean) {

    NavHost(navController = navController, startDestination = if (isIntroFinished) Screens.Host.route + "_" + "Graph" else Screens.Intro.route + "_" + "Graph"){
        introNavigationGraph(navController = navController)
        hostNavigationGraph(navController = navController)
    }

}


fun NavGraphBuilder.introNavigationGraph(navController: NavHostController) {
    navigation(startDestination = Screens.Intro.route , route = Screens.Intro.route + "_" + "Graph") {
        composable(Screens.Intro.route) {
            IntroScreen(navController = navController)
        }

        composable(Screens.NameRegister.route) {
            NameRegisterScreen(navController = navController)
        }

        composable(Screens.Scheduling.route) {
            SchedulingScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.hostNavigationGraph(navController: NavHostController) {
    navigation(startDestination = Screens.Host.route, route = Screens.Host.route + "_" + "Graph") {
        composable(Screens.Host.route) {
            HostScreen(navController = navController)
        }
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.Setting.route) {
            SettingScreen(navController = navController)
        }
    }
}