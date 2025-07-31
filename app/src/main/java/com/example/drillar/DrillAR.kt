package com.example.drillar

import android.app.Application
import com.example.drillar.di.repositoryModule
import com.example.drillar.di.useCaseWrapperModule
import com.example.drillar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DrillAR: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@DrillAR)
            modules(

                repositoryModule,
                useCaseWrapperModule,
                viewModelModule,
            )
        }
    }


}