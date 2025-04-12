package ir.ha.goodfeeling.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.navigation.Screens
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary

@Composable
fun SplashScreen(navController: NavHostController) {

    val introSkipped = true

    GoodFeelingTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightPrimary), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.golden_coin),
                    contentDescription = "splash screen",
                    modifier = Modifier.size(200.dp)
                )
            }


            if (introSkipped){
                navController.navigate(Screens.Host.route){
                    popUpTo(Screens.Host.route) { inclusive = true }
                }
            }else{
                navController.navigate(Screens.Intro.route)
            }


        }
    }
}


@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}