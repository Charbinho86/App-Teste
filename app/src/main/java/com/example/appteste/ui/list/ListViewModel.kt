package com.example.appteste.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appteste.data.entity.AppTesteEntity
import com.example.appteste.domain.repository.AppTesteRepository
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: AppTesteRepository
) : ViewModel() {

    private val _allAppTesteEvents = MutableLiveData<List<AppTesteEntity>>()
    val allAppTesteEvents: LiveData<List<AppTesteEntity>>
    get() = _allAppTesteEvents

    fun getRegisters() = viewModelScope.launch {
        _allAppTesteEvents.postValue(repository.getAll())
    }
}