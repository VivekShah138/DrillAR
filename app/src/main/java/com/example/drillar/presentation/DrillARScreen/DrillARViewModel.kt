package com.example.drillar.presentation.DrillARScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.drillar.domain.usecases.DrillUseCaseWrapper
import com.example.drillar.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DrillARViewModel(
    private val drillUseCaseWrapper: DrillUseCaseWrapper,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(DrillARStates())
    val state: StateFlow<DrillARStates> = _state.asStateFlow()


    private val args = savedStateHandle.toRoute<Screens.DrillARScreen>()
    val id = args.drillId

    init {
        // Initialize with loading state
        _state.value = _state.value.copy(isLoading = true)
        getDrillDetails()
    }

    fun onEvent(events: DrillAREvents) {
        when (events) {
            is DrillAREvents.OnARSessionCreated -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isARInitialized = true,
                    errorMessage = null
                )
            }

            is DrillAREvents.OnPlaneDetected -> {
                _state.value = _state.value.copy(
                    isPlaneDetected = true
                )
            }

            is DrillAREvents.OnDrillPlaced -> {
                _state.value = _state.value.copy(
                    isDrillPlaced = true,
                    drillPosition = events.position,
                    errorMessage = null
                )
            }

            is DrillAREvents.OnDrillRemoved -> {
                _state.value = _state.value.copy(
                    isDrillPlaced = false,
                    drillPosition = null
                )
            }

            is DrillAREvents.OnError -> {
                _state.value = _state.value.copy(
                    errorMessage = events.message,
                    isLoading = false
                )
            }

            is DrillAREvents.OnRetry -> {
                _state.value = _state.value.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
        }
    }

    private fun getDrillDetails(){
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            Log.d("DrillARViewModel", "id -> $id")
            val drillDetail = drillUseCaseWrapper.getDrillDetails(id = id)
            Log.d("DrillARViewModel", "drillDetails $drillDetail")
            _state.update {
                it.copy(
                    selectedDrillName = drillDetail.name,
                    selectedDrillId = drillDetail.id
                )
            }
            Log.d("DrillARViewModel", "states name:${_state.value.selectedDrillName}")
        }
    }
}