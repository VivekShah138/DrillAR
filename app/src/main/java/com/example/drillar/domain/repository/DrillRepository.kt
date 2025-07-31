package com.example.drillar.domain.repository

import com.example.drillar.domain.model.Drill

interface DrillRepository {
    suspend fun getAllDillList(): List<Drill>
    suspend fun getDillDetails(id: Int): Drill
}