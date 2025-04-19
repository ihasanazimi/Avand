package ir.ha.goodfeeling

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
import ir.ha.goodfeeling.common.security_and_permissions.isPermissionGranted
import ir.ha.goodfeeling.navigation.AppNavigator
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
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
            GoodFeelingTheme {
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

            1001 -> {
                lifecycleScope.launch {
                    if (permissions.isNotEmpty()){
                        locationAccessFinePermissionsResult.emit(isPermissionGranted(permissions[0].toString()))
                    }
                }
                return
            }

            1002 -> {
                if (permissions.isNotEmpty()){

                }
            }

            // Handle other permission request codes if needed
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }
}


@Composable
fun GoodFillingApplication(navHostController: NavHostController, activity: MainActivity) {
    GoodFeelingTheme {
        AppNavigator(
            navController = navHostController,
            activity = activity
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoodFeelingTheme {
        val navHostController = rememberNavController()
        GoodFillingApplication(navHostController, MainActivity())
    }
}