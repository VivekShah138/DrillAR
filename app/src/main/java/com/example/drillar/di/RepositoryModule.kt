package com.example.drillar.di

import com.example.drillar.data.repository.DrillRepositoryImpl
import com.example.drillar.domain.repository.DrillRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<DrillRepository> {
        DrillRepositoryImpl()
    }
}