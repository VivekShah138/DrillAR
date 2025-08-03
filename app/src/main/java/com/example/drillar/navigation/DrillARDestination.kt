package com.example.drillar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.drillar.presentation.DrillARScreen.DrillARRoot


fun NavGraphBuilder.drillARGraph(
    navController: NavController
){

    composable<Screens.DrillARScreen> {
        DrillARRoot(
            navigateBackToDrillList = {
                navController.navigate(Screens.DrillListScreen)
            }
        )
    }

}