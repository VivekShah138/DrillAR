package com.example.drillar.di

import com.example.drillar.presentation.DrillARScreen.DrillARViewModel
import com.example.drillar.presentation.DrillDetailsScreen.DrillDetailsViewModel
import com.example.drillar.presentation.DrillListFeature.DrillListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<DrillListViewModel> {
        DrillListViewModel(get())
    }

    viewModel<DrillDetailsViewModel> { parameters ->
        DrillDetailsViewModel(
            drillUseCaseWrapper = get(),
            savedStateHandle = parameters.get()
        )
    }

    viewModel<DrillARViewModel> { parameters ->
        DrillARViewModel(
            drillUseCaseWrapper = get(),
            savedStateHandle = parameters.get()
        )
    }


}