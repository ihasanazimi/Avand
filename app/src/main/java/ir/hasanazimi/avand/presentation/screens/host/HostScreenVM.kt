package ir.hasanazimi.avand.presentation.screens.host

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.db.DataStoreManager
import ir.hasanazimi.avand.presentation.navigation.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostScreenVM @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val TAG = "HostScreenVM"

    val userName = MutableStateFlow<String>("")
    var lastDestination = MutableStateFlow(Screens.Home.routeId) /** home is default */

    fun getUserName() {
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect {
                Log.i(TAG, "getUserName: $it")
                userName.emit(it?:"")
            }
        }
    }


}