package ir.ha.goodfeeling.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    Box(modifier = modifier.fillMaxSize()) {

        Card(
            modifier = modifier.padding(8.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Row(
                modifier
                    .height(260.dp)
                    .background(Color.Transparent)
            ) {

                Card(
                    modifier = modifier
                        .padding(4.dp)
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CardColors(
                        containerColor = LightPrimary,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "32C",
                            style = CustomTypography.bodyLarge,
                            modifier = modifier
                                .weight(0.2f).padding(vertical = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.End
                        )

                        Image(
                            painter = painterResource(id = R.drawable.cloudy),
                            contentDescription = null,
                            modifier = modifier
                                .fillMaxWidth()
                                .weight(0.7f)
                                .alpha(0.7f)
                        )

                        Text(
                            text = "آخ که توی این هوا بستنی میچسبه ✌ ",
                            modifier
                                .padding(top = 24.dp)
                                .fillMaxSize()
                                .weight(0.4f),
                            textAlign = TextAlign.Right
                        )

                    }
                }


                Column(
                    modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {

                    Card(
                        modifier = modifier
                            .padding(4.dp)
                            .weight(1f)
                            .fillMaxWidth(), colors = CardColors(
                            containerColor = LightPrimary,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(modifier = modifier.fillMaxSize()) {

                        }
                    }


                    Card(
                        modifier = modifier
                            .padding(4.dp)
                            .weight(1f)
                            .fillMaxWidth(), colors = CardColors(
                            containerColor = LightPrimary,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(modifier = modifier.fillMaxSize()) {

                        }
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
        HomeScreen(Modifier, navController)
    }
}