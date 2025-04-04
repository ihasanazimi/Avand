package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.navigation.AppNavigator
import ir.ha.goodfeeling.navigation.BottomNavigationBar
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@Composable
fun HostScreen(navController: NavHostController) {

    GoodFeelingTheme {
        Scaffold(
            modifier = Modifier.padding(8.dp),
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                AppNavigator(
                    navController = rememberNavController(),
                    isIntroFinished = false
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        HostScreen(navController = navController)
    }
}