package ir.hasanazimi.avand

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import ir.hasanazimi.avand.common.security_and_permissions.isPermissionGranted
import ir.hasanazimi.avand.data.Const
import ir.hasanazimi.avand.presentation.navigation.AppNavigator
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"
    val locationAccessFinePermissionsResult = MutableSharedFlow<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvandTheme {
                val navHostController = rememberNavController()
                GoodFillingApplication(navHostController = navHostController, activity = this)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        Log.i(TAG, "onRequestPermissionsResult: data: ${Gson().toJson(permissions)}")
        Log.i(TAG, "onRequestPermissionsResult code: $requestCode")
        when (requestCode) {

            Const.LOCATION_PERMEATION_CODE -> {
                lifecycleScope.launch {
                    if (permissions.isNotEmpty()){
                        locationAccessFinePermissionsResult.emit(isPermissionGranted(permissions[0].toString()))
                    }
                }
                return
            }


            else -> {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }
}


@Composable
fun GoodFillingApplication(navHostController: NavHostController, activity: MainActivity) {
    AvandTheme {
        AppNavigator(
            navController = navHostController,
            activity = activity
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AvandTheme {
        val navHostController = rememberNavController()
        GoodFillingApplication(navHostController, MainActivity())
    }
}