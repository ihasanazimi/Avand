package ir.ha.goodfeeling

import android.content.pm.PackageManager
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
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.goodfeeling.common.security_and_permissions.isPermissionGranted
import ir.ha.goodfeeling.navigation.AppNavigator
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"
    val permissionsResult = MutableSharedFlow<Pair<Int , String>>()

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
        Log.i(TAG, "onRequestPermissionsResult: ${permissions.get(0)}")
        when (requestCode) {
            1001 -> {
                lifecycleScope.launch {
                    if (isPermissionGranted(permissions[0].toString())) {
                        // Permission granted, handle accordingly
                        permissionsResult.emit(Pair(1001 , "granted"))
                    } else {
                        // Permission denied, handle accordingly
                        permissionsResult.emit(Pair(1001 , "denied"))
                    }
                }
                return
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