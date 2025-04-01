package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    Surface {
        Box {

        }
    }
}



@Preview(showBackground = true)
@Composable
fun SettingScreenPreview(){
    GoodFeelingTheme {
        SettingScreen(Modifier)
    }
}