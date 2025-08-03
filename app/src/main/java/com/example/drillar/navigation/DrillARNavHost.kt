package com.example.drillar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun DrillARNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screens = Screens.DrillListScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        drillListGraph(
            navController = navController
        )

        drillDetailsGraph(
            navController = navController
        )

        drillARGraph(
            navController = navController
        )
    }
}