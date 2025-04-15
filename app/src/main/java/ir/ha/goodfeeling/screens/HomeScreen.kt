package ir.ha.goodfeeling.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.data.getFakeNews
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current

    GoodFeelingTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                item { Widgets() }
                item {
                    NewsScreen(navController) {
                        Toast.makeText(context, "more news clicked", Toast.LENGTH_SHORT).show()
                    }
                }
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