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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ir.ha.goodfeeling.ui.theme.TransparentlyGray


@Composable
fun NameRegisterScreen(modifier: Modifier = Modifier) {

    GoodFeelingTheme {

        Surface(modifier = modifier.fillMaxSize()) {

            val scrollState = rememberScrollState()
            val textFieldValue = remember { mutableStateOf("") }
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current

            Box(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState) // ✅ اضافه کردن اسکرول عمودی
                        .imePadding() // ✅ جلوگیری از هم‌پوشانی کیبورد
                        .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()) , // ✅ تنظیم فاصله با کیبورد
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
                        text = "چی صدات کنیم؟",
                        modifier = modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.titleLarge
                    )

                    Text(
                        text = "دوستیمون از اینجا شروع میشه",
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.bodyLarge
                    )

                    TextField(
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .align(Alignment.Start)
                            .fillMaxWidth(),
                        value = textFieldValue.value,
                        onValueChange = { characters ->
                            textFieldValue.value = characters
                            if (textFieldValue.value.isEmpty()){
                                focusManager.clearFocus()
                            }
                        },
                        enabled = true,
                        singleLine = true,
                        label = {
                            Text(
                                modifier = modifier.fillMaxWidth(),
                                text = "نام",
                                textAlign = TextAlign.Right,
                                style = CustomTypography.bodyLarge
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = TransparentlyGray,
                            unfocusedContainerColor = TransparentlyGray,
                        ),
                    )
                }

                Button(
                    modifier = modifier
                        .align(Alignment.BottomCenter)
                        .height(58.dp),
                    onClick = {
                        Toast.makeText(context, textFieldValue.value, Toast.LENGTH_SHORT).show()
                    },
                    enabled = textFieldValue.value.isNotEmpty()
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = "ادامه بده",
                        textAlign = TextAlign.Center,
                    )
                }

            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun NameRegisterScreenPreview() {
    GoodFeelingTheme {
        NameRegisterScreen(Modifier)
    }
}







