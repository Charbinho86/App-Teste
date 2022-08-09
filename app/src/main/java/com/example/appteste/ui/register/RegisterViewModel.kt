package com.example.appteste.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appteste.R
import com.example.appteste.domain.repository.AppTesteRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AppTesteRepository
) : ViewModel() {

    private val _appTesteStateEventData = MutableLiveData<AppTesteState>()
    val appTesteStateEventData: LiveData<AppTesteState>
    get() = _appTesteStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
    get() = _messageEventData

    fun addUpdateAppTeste(name: String, email: String, telefone: String, id: Long) {
        if (id > 0 ) {
                updateAppTeste(id, name, email, telefone)
        }   else {
                insertAppTeste(name, email, telefone)
        }
    }

    private fun updateAppTeste(id: Long,
                               name: String,
                               email: String,
                               telefone: String) = viewModelScope.launch {
        try {
            repository.update(id, name, email, telefone)
                _appTesteStateEventData.value = AppTesteState.Updated
                _messageEventData.value = R.string.register_update_successfully
           } catch (ex: Exception) {
            _messageEventData.value = R.string.register_error_to_insert
            Log.e(TAG, ex.toString())
        }
    }

    private fun insertAppTeste(name: String, email: String, telefone: String) = viewModelScope.launch {

        try {
            val id = repository.insert(name, email, telefone)
            if (id > 0) {
                _appTesteStateEventData.value = AppTesteState.Inserted
                _messageEventData.value = R.string.register_inserted_successfully
            }

        } catch (ex: Exception) {
            _messageEventData.value = R.string.register_error_to_insert
            Log.e(TAG, ex.toString())
        }
    }

    sealed class AppTesteState {
        object Inserted: AppTesteState()
        object Updated: AppTesteState()
    }

    companion object {
        private val TAG = RegisterViewModel::class.java.simpleName
    }
}