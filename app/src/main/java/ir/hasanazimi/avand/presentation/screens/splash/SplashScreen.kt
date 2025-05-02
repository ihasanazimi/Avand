package ir.hasanazimi.avand.presentation.screens.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun SplashScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<SplashScreenVM>()
    val coroutineScope = rememberCoroutineScope()

    SideEffect {
        viewModel.getIntroSkipped()
        coroutineScope.launch {
            viewModel.getIntroSkipped().collect { skipped ->
                if (skipped){
                    navController.navigate(Screens.Host.routeId){
                        popUpTo(Screens.Host.routeId) { inclusive = true }
                    }
                }else{
                    navController.navigate(Screens.Intro.routeId)
                }
            }
        }
    }

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
        }
    }
}



@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}