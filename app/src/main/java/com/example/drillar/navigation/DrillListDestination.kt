package com.example.drillar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.drillar.presentation.DrillListFeature.components.DrillListRoot

fun NavGraphBuilder.drillListGraph(
    navController: NavController
){
    composable<Screens.DrillListScreen> {
        DrillListRoot(
            navigateToDetailedScreen = {id ->
                navController.navigate(
                    Screens.DrillDetailsScreen(id = id)
                )
            }
        )
    }
}