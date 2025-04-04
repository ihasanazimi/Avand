package ir.ha.goodfeeling.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.navigation.Screens
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


@Composable
fun IntroScreen(modifier: Modifier = Modifier, navController: NavController) {

    GoodFeelingTheme {

        Scaffold(
            modifier = modifier ,
            bottomBar = {
                Button(
                    modifier = modifier
                        .padding(16.dp)
                        .height(58.dp),
                    onClick = {
                        navController.navigate(Screens.NameRegister.route)
                    },
                    enabled = true
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = stringResource(R.string.go_start),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        ) { innerPadding ->

            Column(modifier = modifier.fillMaxSize().padding(innerPadding)) {

                Box(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                ) {

                    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {

                        Image(
                            painterResource(id = R.drawable.intro),
                            contentDescription = "this is description",
                            modifier = modifier
                                .size(350.dp)
                                .align(Alignment.CenterHorizontally),
                        )

                        Text(
                            text = stringResource(R.string.intro_title),
                            modifier = modifier
                                .padding(bottom = 8.dp, top = 42.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = CustomTypography.titleLarge
                        )


                        Text(
                            text = stringResource(R.string.intro_des),
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            lineHeight = TextUnit(24f, TextUnitType.Sp),
                            style = CustomTypography.bodyLarge,
                        )

                    }
                }

            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        IntroScreen(navController = navController)
    }
}







