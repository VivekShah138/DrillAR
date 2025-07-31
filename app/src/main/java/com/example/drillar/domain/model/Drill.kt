package com.example.drillar.domain.model

data class Drill(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val description: String,
    val tips: List<String>
)
