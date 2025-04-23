package ir.hasanazimi.avand.screens.bottom_sheets

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.hasanazimi.avand.ui.theme.AvandTheme
import ir.hasanazimi.avand.ui.theme.CustomTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileBottomSheet(
    lastUserName: String,
    isOpen: Boolean,
    newUserName: (userName: String) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var temp by remember { mutableStateOf("") }

    if (isOpen) {

        if (lastUserName.isNotEmpty()){
            temp = lastUserName
        }

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                if (temp == lastUserName) {
                    newUserName.invoke(lastUserName)
                }else{
                    newUserName.invoke(temp)
                }
            },
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(),
                        value = temp,
                        onValueChange = { temp = it },
                        label = {
                            Text(
                                text = "چی صدات کنیم؟",
                                style = CustomTypography.labelSmall
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                    )
                }


                Button(
                    enabled = temp.isNotEmpty(),
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        if (temp == lastUserName) {
                            newUserName.invoke(lastUserName)
                        }else{
                            newUserName.invoke(temp)
                        }
                    }) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "تایید",
                            style = CustomTypography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
private fun UserProfileBottomSheetPreview() {
    AvandTheme {
        UserProfileBottomSheet(
            lastUserName = "حسن عظیمی",
            isOpen = true
        ) {
            Log.i("TAG", "CitiesModalBottomSheetPreview: $it ")
        }
    }
}
