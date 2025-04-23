package ir.hasanazimi.avand.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme

@Composable
fun SplashScreen(navController: NavHostController) {

    val introSkipped = true

    AvandTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary.copy(0.2f)), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.golden_coin),
                    contentDescription = "splash screen",
                    modifier = Modifier.size(200.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
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