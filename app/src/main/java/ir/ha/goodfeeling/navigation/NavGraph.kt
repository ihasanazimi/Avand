package ir.ha.goodfeeling.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ir.ha.goodfeeling.MainActivity
import ir.ha.goodfeeling.common.more.IntentActionsHelper
import ir.ha.goodfeeling.screens.AboutUsScreen
import ir.ha.goodfeeling.screens.CurrencyPricesScreen
import ir.ha.goodfeeling.screens.HomeScreen
import ir.ha.goodfeeling.screens.HostScreen
import ir.ha.goodfeeling.screens.IntroScreen
import ir.ha.goodfeeling.screens.NameRegisterScreen
import ir.ha.goodfeeling.screens.NewsScreen
import ir.ha.goodfeeling.screens.SchedulingScreen
import ir.ha.goodfeeling.screens.SettingScreen
import ir.ha.goodfeeling.screens.SplashScreen
import ir.ha.goodfeeling.screens.WebViewScreen


val splashNavGraph = "splashGraph"
val hostNavGraph = "hostGraph"

@Composable
fun AppNavigator(navController: NavHostController, activity: MainActivity) {

    NavHost(navController = navController, startDestination = splashNavGraph) {
        splashGraph(navController, activity)
        hostGraph(navController, activity)
    }

}


fun NavGraphBuilder.splashGraph(navController: NavHostController, activity: MainActivity) {
    navigation(startDestination = Screens.Splash.route, route = splashNavGraph) {

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
            AboutUsScreen(
                navHostController = navController,
                onGithubClick = { IntentActionsHelper(activity).openWebSite("https://github.com/ihasanazimi") },
                onLinkedinClick = { IntentActionsHelper(activity).openLinkedInPage("ihasanazimi") },
                onMessageClick = { IntentActionsHelper(activity).sendEmail("ihasan.azimi@gmail.com") }
            )
        }

        composable(Screens.News.route) {
            NewsScreen(navController) {

            }
        }

        composable(
            route = Screens.WebView.route + "/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url").orEmpty()
            val url = Uri.decode(encodedUrl)
            WebViewScreen(url) {
                navController.popBackStack()
            }
        }
    }
}


fun NavGraphBuilder.hostGraph(navController: NavHostController, activity: MainActivity) {
    navigation(startDestination = Screens.Host.route, route = hostNavGraph) {
        composable(Screens.Host.route) {
            HostScreen(navController)
        }

        composable(Screens.Home.route) {
            HomeScreen(navController)
        }


        composable(Screens.CurrencyPrices.route) {
            CurrencyPricesScreen(navController)
        }

        composable(Screens.Setting.route) {
            SettingScreen(navController)
        }
    }
}

