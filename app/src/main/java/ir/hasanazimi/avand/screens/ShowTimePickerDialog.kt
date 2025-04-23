package ir.hasanazimi.avand.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hasanazimi.avand.ui.theme.AvandTheme
import ir.hasanazimi.avand.ui.theme.getBackgroundColor


sealed class TimePickerFlag (val flag : String) {
    object BedTime : TimePickerFlag("BedTime")
    object WakeUpTime : TimePickerFlag("WakeUpTime")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTimePickerDialog(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    timerPickerFlag : TimePickerFlag,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int , MutableState<String> , TimePickerFlag ) -> Unit
) {
    if (showDialog) {

        val timePickerState =
            rememberTimePickerState(initialHour = 0, initialMinute = 0, is24Hour = true)

        DatePickerDialog(
            colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(
                    modifier = modifier.padding(horizontal = 8.dp), onClick = {
                        val selectedTime = mutableStateOf("%02d:%02d".format(timePickerState.hour, timePickerState.minute))
                        onTimeSelected(timePickerState.hour, timePickerState.minute,selectedTime,timerPickerFlag)
                        onDismiss()
                    }) {
                    Text("ذخیره")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    onClick = { onDismiss() }) {
                    Text("لغو", color = MaterialTheme.colorScheme.primary)
                }
            }
        ) {
            Column(modifier = modifier.padding(vertical = 4.dp)) {
                Text(
                    text = dialogTitle,
                    modifier = modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(24.dp)
                ) {
                    TimePicker(
                        state = timePickerState,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = TimePickerDefaults.colors(
                            clockDialColor = getBackgroundColor(),
                            clockDialSelectedContentColor = getBackgroundColor(),
                            clockDialUnselectedContentColor = MaterialTheme.colorScheme.primary,
                            selectorColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.primary,
                            periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                            periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.primary,
                            periodSelectorSelectedContentColor = MaterialTheme.colorScheme.primary,
                            periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.primary,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            timeSelectorSelectedContentColor = Color.White,
                            timeSelectorUnselectedContentColor = Color.White
                        )
                    )
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun ShowTimePickerDialogPreview() {
    AvandTheme {
        var showDialog by remember { mutableStateOf(true) }
        var selectedTime = remember { mutableStateOf("") }
        val timerPickerFlag  = TimePickerFlag.BedTime

        ShowTimePickerDialog(
            modifier = Modifier,
            dialogTitle = "زمان انتخابی شما",
            timerPickerFlag = timerPickerFlag,
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onTimeSelected = { hour, minute , selectedTime , timePickerFlag ->

            }
        )
    }
}
