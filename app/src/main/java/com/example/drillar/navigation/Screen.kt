package com.example.drillar.navigation

import kotlinx.serialization.Serializable

sealed class Screens{
    @Serializable
    data object DrillListScreen: Screens()

    @Serializable
    data class DrillDetailsScreen(val id: Int): Screens()
}
