package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.navigation.BottomNavigationBar
import ir.ha.goodfeeling.navigation.Screens
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@Composable
fun HostScreen() {
    GoodFeelingTheme {

        val hostNavController = rememberNavController()

        Scaffold(
            modifier = Modifier.padding(8.dp),
            bottomBar = {
                BottomNavigationBar(navController = hostNavController)
            }
        ) { innerPadding ->
            Surface {
                NavHost(
                    navController = hostNavController,
                    startDestination = Screens.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Screens.Home.route) {
                        HomeScreen(modifier = Modifier, navController = hostNavController)
                    }
                    composable(route = Screens.Setting.route) {
                        SettingScreen(modifier = Modifier, navController = hostNavController)
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    GoodFeelingTheme {
        HostScreen()
    }
}