package com.example.drillar.di

import com.example.drillar.presentation.DrillDetailsScreen.DrillDetailsViewModel
import com.example.drillar.presentation.DrillListFeature.DrillListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        DrillListViewModel(get())
    }

    viewModel { parameters ->
        DrillDetailsViewModel(
            drillUseCaseWrapper = get(),
            savedStateHandle = parameters.get()
        )
    }
}