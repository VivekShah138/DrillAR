package com.example.drillar.presentation.DrillDetailsScreen

data class DrillDetailsStates(
    val description: String  = "",
    val name: String = "",
    val imageResId: Int = 0,
    val tips : List<String> = emptyList(),
    val isLoading: Boolean = false,
    val drillId: Int? = null
)