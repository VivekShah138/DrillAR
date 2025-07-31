package com.example.drillar.presentation.DrillListFeature

sealed interface DrillListEvents {
    data class OnDrillClick(val id: Int): DrillListEvents
}