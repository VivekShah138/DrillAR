package com.example.drillar.domain.usecases

import com.example.drillar.domain.model.Drill
import com.example.drillar.domain.repository.DrillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllDrillList(
    private val drillRepository: DrillRepository
) {
    suspend operator fun invoke(): List<Drill> = withContext(Dispatchers.IO){
        drillRepository.getAllDillList()
    }
}