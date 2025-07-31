package com.example.drillar.di

import com.example.drillar.domain.usecases.DrillUseCaseWrapper
import com.example.drillar.domain.usecases.GetAllDrillList
import com.example.drillar.domain.usecases.GetDrillDetails
import org.koin.dsl.module

val useCaseWrapperModule = module{
    single<DrillUseCaseWrapper> {
        DrillUseCaseWrapper(
            getDrillDetails = GetDrillDetails(get()),
            getAllDrillList = GetAllDrillList(get())
        )
    }
}