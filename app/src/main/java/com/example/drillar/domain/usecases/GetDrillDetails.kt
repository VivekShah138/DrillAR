package com.example.drillar.domain.usecases

import com.example.drillar.domain.model.Drill
import com.example.drillar.domain.repository.DrillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDrillDetails(
    private val drillRepository: DrillRepository
) {
    suspend operator fun invoke(id: Int): Drill = withContext(Dispatchers.IO){
        drillRepository.getDillDetails(id = id)
    }
}