package com.example.drillar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.drillar.presentation.DrillDetailsScreen.components.DrillDetailsRoot

fun NavGraphBuilder.drillDetailsGraph(
    navController: NavController
){
    composable<Screens.DrillDetailsScreen> {
        DrillDetailsRoot(
            navigateBackToDrillList = {
                navController.popBackStack()
            }
        )
    }
}