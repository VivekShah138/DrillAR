package com.example.drillar.presentation.DrillListFeature

import com.example.drillar.domain.model.Drill

data class DrillListStates(
    val drillList: List<Drill> = emptyList(),
    val isLoading: Boolean = false,
)