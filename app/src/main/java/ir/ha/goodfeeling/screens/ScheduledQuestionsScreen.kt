@file:OptIn(ExperimentalMaterial3Api::class)

package ir.ha.goodfeeling.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


@Composable
fun SchedulingScreen(modifier: Modifier = Modifier) {

    GoodFeelingTheme {

        Surface(modifier = modifier.fillMaxSize()) {

            val scrollState = rememberScrollState()
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current
            val bedTime = remember { mutableStateOf("") }
            val wakeUpTime = remember { mutableStateOf("") }

            var showDialog by remember { mutableStateOf(false) }
            var selectedTime by remember { mutableStateOf("ساعت انتخابی: 07:07") }

            Box(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .imePadding()
                        .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()) ,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painterResource(id = R.drawable.intro),
                        contentDescription = "this is description",
                        modifier = modifier
                            .padding(top = 32.dp)
                            .size(350.dp)
                            .align(Alignment.CenterHorizontally),
                    )

                    Text(
                        text = "زمان های استراحتت چطوره؟",
                        modifier = modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.titleLarge
                    )

                    Text(
                        text = "میخوام طبق برنامه ریزی خوابت کنارت باشم.",
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.bodyLarge
                    )

                    Column {

                        OutlinedButton(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            onClick = {

                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = " معمولا کی بیدار میشی؟ ${bedTime.value} ")
                        }



                        OutlinedButton(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            onClick = {

                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = " معمولا کی میخوابی؟ ${wakeUpTime.value}")
                        }


                    }



                }

                Button(
                    modifier = modifier
                        .align(Alignment.BottomCenter)
                        .height(58.dp),
                    onClick = {
                        showDialog = true
                    },
                    enabled = true
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = "بزن بریم",
                        textAlign = TextAlign.Center,
                    )
                }


                ShowTimePickerDialog(
                    modifier = modifier,
                    dialogTitle = "زمان انتخابی شما",
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    onTimeSelected = { hour, minute ->
                        selectedTime = "ساعت انتخابی: %02d:%02d".format(hour, minute)
                        Toast.makeText(context,selectedTime, Toast.LENGTH_LONG).show()
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SchedulingScreenPreview() {
    GoodFeelingTheme {
        SchedulingScreen(Modifier)
    }
}







