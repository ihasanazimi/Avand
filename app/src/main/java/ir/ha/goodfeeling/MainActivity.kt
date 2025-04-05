package ir.ha.goodfeeling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.navigation.AppNavigator
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoodFeelingTheme {
                val navHostController = rememberNavController()
                GoodFillingApplication(navHostController)
            }
        }
    }
}


@Composable
fun GoodFillingApplication(navHostController: NavHostController) {
    AppNavigator(navController = navHostController)
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoodFeelingTheme {
        val navHostController = rememberNavController()
        GoodFillingApplication(navHostController)
    }
}