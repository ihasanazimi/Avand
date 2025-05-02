package ir.hasanazimi.avand.presentation.screens.scheduled_questions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledQuestionsScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel(){

    val TAG = "ScheduledQuestionsScreenVM"
    val wakeUpTimeData = MutableStateFlow("")
    val bedTimeData = MutableStateFlow("")


    fun saveBedTime(bedTime : String){
        viewModelScope.launch {
            dataStoreManager.saveBedTime(bedTime).also {
                Log.i(TAG, "saveBedTime: $bedTime")
            }
        }
    }


    fun saveWakeUpTime(wakeupTime : String){
        viewModelScope.launch {
            dataStoreManager.saveWakeTime(wakeupTime).also {
                Log.i(TAG, "saveWakeUpTime: $wakeupTime")
            }
        }
    }


    fun saveIntroSkipped(skipped : Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveIntroSkipped(skipped).also {
                Log.i(TAG, "saveIntroSkipped: skipped is $skipped")
            }
        }
    }

    fun getWakeUpTime(){
        viewModelScope.launch {
            dataStoreManager.wakeTimeFlow.collect {
                wakeUpTimeData.emit(it?:"")
            }
        }
    }

    fun getBedTime(){
        viewModelScope.launch {
            dataStoreManager.bedTimeFlow.collect {
                bedTimeData.emit(it?:"")
            }
        }
    }

}