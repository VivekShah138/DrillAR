package com.example.drillar.presentation.DrillDetailsScreen

import com.example.drillar.presentation.DrillListFeature.DrillListEvents

sealed interface DrillDetailsEvents {
    data class OnARClick(val id: Int): DrillDetailsEvents
}