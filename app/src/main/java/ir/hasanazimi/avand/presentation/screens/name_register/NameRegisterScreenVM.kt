package ir.hasanazimi.avand.presentation.screens.name_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.avand.db.DataStoreManager
import kotlinx.coroutines.launch
import javax.inject.Inject


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
