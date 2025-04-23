package ir.hasanazimi.avand.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.more.IntentActionsHelper
import ir.hasanazimi.avand.screens.AboutUsScreen
import ir.hasanazimi.avand.screens.CurrencyPricesScreen
import ir.hasanazimi.avand.screens.HomeScreen
import ir.hasanazimi.avand.screens.HostScreen
import ir.hasanazimi.avand.screens.IntroScreen
import ir.hasanazimi.avand.screens.NameRegisterScreen
import ir.hasanazimi.avand.screens.NewsScreen
import ir.hasanazimi.avand.screens.SchedulingScreen
import ir.hasanazimi.avand.screens.SettingScreen
import ir.hasanazimi.avand.screens.SplashScreen


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

      /*  composable(
            route = Screens.WebView.route + "/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url").orEmpty()
            val url = Uri.decode(encodedUrl)
            WebViewScreen(url = url, ) {
                navController.popBackStack()
            }
        }*/
    }
}


fun NavGraphBuilder.hostGraph(navController: NavHostController, activity: MainActivity) {
    navigation(startDestination = Screens.Host.route, route = hostNavGraph) {
        composable(Screens.Host.route) {
            HostScreen(activity = activity , navController = navController)
        }

        composable(Screens.Home.route) {
            HomeScreen(activity = activity , navController = navController)
        }


        composable(Screens.CurrencyPrices.route) {
            CurrencyPricesScreen(navController)
        }

        composable(Screens.Setting.route) {
            SettingScreen(navController)
        }
    }
}

