package ir.hasanazimi.avand.presentation.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.db.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class SplashScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel(){

    val TAG = "SplashScreenVM"

    fun getIntroSkipped() = dataStoreManager.introSkipped.also {
        Log.i(TAG, "getIntroSkipped: skipped is $it")
    }

}
