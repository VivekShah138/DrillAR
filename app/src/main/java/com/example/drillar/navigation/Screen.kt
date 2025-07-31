package com.example.drillar.navigation

sealed class Screens{
    data object DrillListScreen: Screens()
    data class DrillDetailsScreen(val id: Int): Screens()
}
