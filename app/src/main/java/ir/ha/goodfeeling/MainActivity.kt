package ir.ha.goodfeeling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.goodfeeling.navigation.AppNavigator
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoodFeelingTheme {
                val navHostController = rememberNavController()
                GoodFillingApplication(navHostController = navHostController, activity = this)
            }
        }
    }
}


@Composable
fun GoodFillingApplication(navHostController: NavHostController, activity: MainActivity) {
    GoodFeelingTheme {
        AppNavigator(
            navController = navHostController,
            activity = activity
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoodFeelingTheme {
        val navHostController = rememberNavController()
        GoodFillingApplication(navHostController , MainActivity())
    }
}