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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.navigation.Screens
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import kotlinx.coroutines.launch
import javax.inject.Inject


@Composable
fun NameRegisterScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<NameRegisterScreenVM>()
    val scrollState = rememberScrollState()
    var userNameState by remember { mutableStateOf("") }
    val maxUserNameCharacter = 20

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    AvandTheme {

        Scaffold(bottomBar = {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .height(58.dp),
                onClick = {
                    viewModel.nameRegistration(userNameState).also {
                        navController.navigate(Screens.Scheduling.routeId)
                    }
                },
                enabled = userNameState.isNotEmpty()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ادامه بده",
                    textAlign = TextAlign.Center,
                )
            }
        }) { innerPadding ->

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .imePadding()
                        .padding(
                            bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
                        ),
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
                        text = "چی صدات کنیم؟",
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.titleLarge
                    )

                    Text(
                        text = "دوستیمون از اینجا شروع میشه",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        style = CustomTypography.bodyLarge
                    )

                    TextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(Alignment.Start)
                            .fillMaxWidth(),
                        value = userNameState.take(maxUserNameCharacter),
                        onValueChange = { characters ->
                            userNameState = characters.take(maxUserNameCharacter)
                            if (userNameState.isEmpty()) {
                                focusManager.clearFocus()
                            }
                        },
                        enabled = true,
                        singleLine = true,
                        label = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "نام",
                                textAlign = TextAlign.Right,
                                style = CustomTypography.bodyLarge
                            )
                        },
                    )
                }

            }
        }

    }
}



@HiltViewModel
class NameRegisterScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel(){

    fun nameRegistration(newName : String){
        viewModelScope.launch {
            dataStoreManager.saveUserName(newName)
        }
    }


}


@Preview(showBackground = true)
@Composable
fun NameRegisterScreenPreview() {
    AvandTheme {
        val nav = rememberNavController()
        NameRegisterScreen(navController = nav)
    }
}







