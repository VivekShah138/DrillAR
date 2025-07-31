package com.example.drillar.presentation.DrillDetailsScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drillar.domain.usecases.DrillUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DrillDetailsViewModel(
    private val drillUseCaseWrapper: DrillUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(DrillDetailsStates())
    val state: StateFlow<DrillDetailsStates> = _state.asStateFlow()

    init {
        getDrillDetails()
    }

    fun onEvent(events: DrillDetailsEvents) {
        when (events) {
            else -> TODO("Handle actions")
        }
    }

    private fun getDrillDetails(){
        viewModelScope.launch {
            val drillDetail = drillUseCaseWrapper.getDrillDetails(1)
            _state.update {
                it.copy(
                    name = drillDetail.name,
                    description = drillDetail.description,
                    imageResId = drillDetail.imageResId,
                    tips = drillDetail.tips
                )
            }
        }
    }
}