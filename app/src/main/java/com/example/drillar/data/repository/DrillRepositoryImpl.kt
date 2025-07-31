package com.example.drillar.data.repository

import com.example.drillar.data.data_source.DummyDrillData
import com.example.drillar.domain.model.Drill
import com.example.drillar.domain.repository.DrillRepository

class DrillRepositoryImpl(

): DrillRepository {
    override suspend fun getAllDillList(): List<Drill> {
        return DummyDrillData.dummyDrillList
    }

    override suspend fun getDillDetails(id: Int): Drill {
        return DummyDrillData.dummyDrillList.first { it.id == id }
    }

}