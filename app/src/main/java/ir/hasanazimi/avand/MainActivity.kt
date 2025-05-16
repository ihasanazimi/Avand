package ir.hasanazimi.avand

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"
    private val _locationAccessFinePermissionsResult = MutableSharedFlow<Boolean>()
    val locationAccessFinePermissionsResult = _locationAccessFinePermissionsResult.asSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvandTheme {
                val mainNavHostController = rememberNavController()
                AvandApp(
                    navHostController = mainNavHostController,
                    activity = this@MainActivity
                )
            }
        }
    }


    @Composable
    fun AvandApp(navHostController: NavHostController, activity: MainActivity) {
        AvandTheme {
            Surface {
                AppNavigator(
                    navController = navHostController,
                    activity = activity
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        Log.i(TAG, "onRequestPermissionsResult \nrequestCode: $requestCode\npermissions: ${Gson().toJson(permissions)}")
        when (requestCode) {
            Const.LOCATION_PERMEATION_CODE -> {
                lifecycleScope.launch {
                    if (permissions.isNotEmpty()){
                        _locationAccessFinePermissionsResult.emit(isPermissionGranted(permissions[0].toString()))
                    }
                }
                return
            }
            else -> {}
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }


    fun locationAccessFinePermissionsResult(){
        Log.i(TAG, "locationAccessFinePermissionsResult: ")
        lifecycleScope.launch {
            val checkIt = true
            _locationAccessFinePermissionsResult.emit(checkIt)
        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AvandTheme {
        val navHostController = rememberNavController()
        val activity = MainActivity()
        activity.AvandApp(
            navHostController,
            activity
        )
    }
}