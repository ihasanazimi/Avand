@file:OptIn(ExperimentalMaterial3Api::class)

package ir.hasanazimi.avand.presentation.screens

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@Composable
fun SchedulingScreen(
    navController: NavHostController
) {

    AvandTheme {

        Surface(modifier = Modifier.fillMaxSize()) {

            val scrollState = rememberScrollState()
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current

            var showTimePicker by remember { mutableStateOf(false) }
            var timePickerFlag by remember { mutableStateOf<TimePickerFlag>(TimePickerFlag.BedTime) }
            val bedTimeData = remember { mutableStateOf("") }
            val wakeUpTimeData = remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .imePadding()
                        .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()) ,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painterResource(id = R.drawable.intro),
                        contentDescription = "this is description",
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .size(350.dp)
                            .align(Alignment.CenterHorizontally),
                    )

                    Text(
                        text = "زمان های استراحتت چطوره؟",
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.titleLarge
                    )

                    Text(
                        text = "میخوام طبق برنامه ریزی خوابت کنارت باشم.",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.bodyLarge
                    )

                    Column {


                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            onClick = {
                                timePickerFlag = TimePickerFlag.BedTime
                                showTimePicker = true
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "    معمولا کی میخوابی؟ ${bedTimeData.value}")
                        }

                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            onClick = {
                                timePickerFlag = TimePickerFlag.WakeUpTime
                                showTimePicker = true
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "    معمولا کی بیدار میشی؟ ${wakeUpTimeData.value} ")
                        }


                    }



                }

                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .height(58.dp),
                    onClick = {
                        navController.navigate(Screens.Host.route){
                            popUpTo(Screens.Scheduling.route) { inclusive = true }
                        }
                    },
                    enabled = true
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "بزن بریم",
                        textAlign = TextAlign.Center,
                    )
                }


                ShowTimePickerDialog(
                    modifier = Modifier,
                    dialogTitle = "زمان انتخابی شما",
                    timerPickerFlag = timePickerFlag,
                    showDialog = showTimePicker,
                    onDismiss = { showTimePicker = false },
                    onTimeSelected = { hour, minute, fullyTimeData, flag ->
                        when (flag) {
                            TimePickerFlag.BedTime -> bedTimeData.value = fullyTimeData.value
                            TimePickerFlag.WakeUpTime -> wakeUpTimeData.value = fullyTimeData.value
                        }
                        showTimePicker = false
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SchedulingScreenPreview() {
    AvandTheme {
        val navController = rememberNavController()
        SchedulingScreen(navController = navController)
    }
}







