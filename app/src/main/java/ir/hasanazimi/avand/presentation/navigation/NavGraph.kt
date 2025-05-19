package ir.hasanazimi.avand.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.more.IntentActionsHelper
import ir.hasanazimi.avand.presentation.screens.about_us.AboutUsScreen
import ir.hasanazimi.avand.presentation.screens.currency_prices.CurrencyPricesScreen
import ir.hasanazimi.avand.presentation.screens.home.HomeScreen
import ir.hasanazimi.avand.presentation.screens.host.HostScreen
import ir.hasanazimi.avand.presentation.screens.intro.IntroScreen
import ir.hasanazimi.avand.presentation.screens.name_register.NameRegisterScreen
import ir.hasanazimi.avand.presentation.screens.news.AllNewsScreen
import ir.hasanazimi.avand.presentation.screens.news.NewsScreen
import ir.hasanazimi.avand.presentation.screens.scheduled_questions.SchedulingScreen
import ir.hasanazimi.avand.presentation.screens.setting.SettingScreen
import ir.hasanazimi.avand.presentation.screens.splash.SplashScreen


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
    navigation(startDestination = Screens.Splash.routeId, route = splashNavGraph) {

        composable(Screens.Splash.routeId) {
            SplashScreen(navController = navController)
        }

        composable(Screens.Intro.routeId) {
            IntroScreen(navController = navController)
        }

        composable(Screens.NameRegister.routeId) {
            NameRegisterScreen(navController = navController)
        }

        composable(Screens.Scheduling.routeId) {
            SchedulingScreen(navController = navController)
        }

        composable(Screens.AboutUs.routeId) {
            AboutUsScreen(
                navHostController = navController,
                onGithubClick = { IntentActionsHelper(activity).openWebSite("https://github.com/ihasanazimi") },
                onLinkedinClick = { IntentActionsHelper(activity).openLinkedInPage("ihasanazimi") },
                onMessageClick = { IntentActionsHelper(activity).sendEmail("ihasan.azimi@gmail.com") }
            )
        }

        composable(Screens.AllNews.routeId) {
            AllNewsScreen(
                navController = navController ,
                activity = activity
            )
        }


    }
}


fun NavGraphBuilder.hostGraph(navController: NavHostController, activity: MainActivity) {
    navigation(startDestination = Screens.Host.routeId, route = hostNavGraph) {
        composable(Screens.Host.routeId) {
            HostScreen(
                activity = activity ,
                navController = navController
            )
        }

        composable(Screens.Home.routeId) {
            HomeScreen(
                activity = activity ,
                navController = navController
            )
        }


        composable(Screens.CurrencyPrices.routeId) {
            CurrencyPricesScreen(
                activity = activity,
                navController = navController
            )
        }

        composable(Screens.Setting.routeId) {
            SettingScreen(
                activity = activity,
                navController = navController
            )
        }
    }
}

