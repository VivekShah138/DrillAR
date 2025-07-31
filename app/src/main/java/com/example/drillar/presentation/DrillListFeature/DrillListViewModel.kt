package com.example.drillar.presentation.DrillListFeature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drillar.domain.usecases.DrillUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DrillListViewModel(
    private val drillUseCaseWrapper: DrillUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(DrillListStates())
    val state: StateFlow<DrillListStates> = _state.asStateFlow()

    init {
        getDrillDetails()
    }

    fun onEvent(events: DrillListEvents) {
        when (events) {
            else -> TODO("Handle actions")
        }
    }

    private fun getDrillDetails(){
        viewModelScope.launch {
            val drillList = drillUseCaseWrapper.getAllDrillList()
            _state.update {
                it.copy(
                    drillList = drillList
                )
            }
        }
    }
}