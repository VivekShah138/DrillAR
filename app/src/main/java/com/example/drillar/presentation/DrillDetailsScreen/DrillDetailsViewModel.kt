package com.example.drillar.presentation.DrillDetailsScreen

import android.util.Log
import androidx.collection.intSetOf
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


class DrillDetailsViewModel(
    private val drillUseCaseWrapper: DrillUseCaseWrapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DrillDetailsStates())
    val state: StateFlow<DrillDetailsStates> = _state.asStateFlow()

    private val args = savedStateHandle.toRoute<Screens.DrillDetailsScreen>()
    val id = args.id

    init {
        getDrillDetails()
    }

    fun onEvent(events: DrillDetailsEvents) {
        when (events) {
            else -> TODO("Handle actions")
        }
    }

    private fun getDrillDetails(){
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            Log.d("DrillDetailsViewModel", "id -> $id")
            val drillDetail = drillUseCaseWrapper.getDrillDetails(id = id)
            Log.d("DrillDetailsViewModel", "drillDetails $drillDetail")
            _state.update {
                it.copy(
                    name = drillDetail.name,
                    description = drillDetail.description,
                    imageResId = drillDetail.imageResId,
                    tips = drillDetail.tips,
                    isLoading = false,
                    drillId = id
                )
            }
        }
    }
}