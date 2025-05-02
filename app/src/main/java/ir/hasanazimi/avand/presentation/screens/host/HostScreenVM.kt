package ir.hasanazimi.avand.presentation.screens.host

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val TAG = "HostScreenVM"

    val userName = MutableStateFlow<String>("")

    fun getUserName() {
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect {
                Log.i(TAG, "getUserName: $it")
                userName.emit(it?:"")
            }
        }
    }


}