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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.Purple80


@Composable
fun IntroScreen(md: Modifier = Modifier) {

    GoodFeelingTheme {

        Surface(
            md.fillMaxSize()
        ) {

            Box(
                modifier = md
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Column(md.fillMaxWidth(), verticalArrangement = Arrangement.Center) {

                    Image(
                        painterResource(id = R.drawable.intro),
                        contentDescription = "this is description",
                        modifier = md
                            .size(300.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = stringResource(R.string.intro_title),
                        modifier = md
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )


                    Text(
                        text = stringResource(R.string.intro_des),
                        modifier = md
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                }



                Button(
                    modifier = md
                        .align(Alignment.BottomCenter)
                        .height(58.dp),
                    onClick = {
                        // todo
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Purple80, containerColor = Purple80
                    ),
                ) {
                    Text(
                        modifier = md.fillMaxWidth(),
                        text = stringResource(R.string.go_start),
                        textAlign = TextAlign.Center,
                    )
                }

            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    IntroScreen(Modifier)
}







