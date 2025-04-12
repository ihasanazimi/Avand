package ir.ha.goodfeeling.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.ha.goodfeeling.screens.AboutUsScreen
import ir.ha.goodfeeling.screens.HomeScreen
import ir.ha.goodfeeling.screens.HostScreen
import ir.ha.goodfeeling.screens.IntroScreen
import ir.ha.goodfeeling.screens.NameRegisterScreen
import ir.ha.goodfeeling.screens.SchedulingScreen
import ir.ha.goodfeeling.screens.SettingScreen
import ir.ha.goodfeeling.screens.SplashScreen


val splashNavGraph = "splashGraph"
val hostNavGraph = "hostGraph"

@Composable
fun AppNavigator(navController: NavHostController) {

    NavHost(navController = navController, startDestination = splashNavGraph ) {
        splashGraph(navController)
        hostGraph(navController)
    }

}



fun NavGraphBuilder.splashGraph(navController: NavHostController){
    navigation(startDestination = Screens.Splash.route , route = splashNavGraph) {

        composable(Screens.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screens.Intro.route) {
            IntroScreen(navController)
        }

        composable(Screens.NameRegister.route) {
            NameRegisterScreen(navController)
        }

        composable(Screens.Scheduling.route) {
            SchedulingScreen(navController)
        }

        composable(Screens.AboutUs.route) {
            AboutUsScreen(navController)
        }
    }
}


fun NavGraphBuilder.hostGraph(navController: NavHostController){
    navigation(startDestination = Screens.Host.route , route = hostNavGraph) {
        composable(Screens.Host.route) {
            HostScreen(navController)
        }

        composable(Screens.Home.route) {
            HomeScreen(navController)
        }

        composable(Screens.Setting.route) {
            SettingScreen(navController)
        }
    }
}

