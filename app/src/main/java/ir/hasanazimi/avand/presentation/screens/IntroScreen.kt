package ir.hasanazimi.avand.presentation.screens

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@Composable
fun IntroScreen(navController: NavHostController) {

    AvandTheme {

        Scaffold(
            modifier = Modifier ,
            bottomBar = {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(58.dp),
                    onClick = {
                        navController.navigate(Screens.NameRegister.routeId)
                    },
                    enabled = true
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.go_start),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        ) { innerPadding ->

            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                ) {

                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {

                        Image(
                            painterResource(id = R.drawable.intro),
                            contentDescription = "this is description",
                            modifier = Modifier
                                .size(350.dp)
                                .align(Alignment.CenterHorizontally),
                        )

                        Text(
                            text = stringResource(R.string.intro_title),
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 42.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = CustomTypography.titleLarge
                        )


                        Text(
                            text = stringResource(R.string.intro_des),
                            modifier = Modifier
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
    AvandTheme {
        val navController = rememberNavController()
        IntroScreen(navController = navController)
    }
}







