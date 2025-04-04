package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


@Composable
fun SettingScreen(modifier: Modifier = Modifier,navController: NavController) {
    Surface {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("محتوای صفحه تنظیمات")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SettingScreenPreview(){
    GoodFeelingTheme {
        val navController = rememberNavController()
        SettingScreen(Modifier,navController)
    }
}