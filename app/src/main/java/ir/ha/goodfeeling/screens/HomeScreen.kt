package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    GoodFeelingTheme {
        Surface {
            Column(modifier = Modifier
                .fillMaxSize()
                /*.height(800.dp)*/
                /*.verticalScroll(rememberScrollState())*/
                .padding(horizontal = 8.dp)
            ) {
                Widgets()
                CurrencyPricesScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}