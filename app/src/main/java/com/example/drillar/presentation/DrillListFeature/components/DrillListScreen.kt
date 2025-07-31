package com.example.drillar.presentation.DrillListFeature.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drillar.presentation.DrillListFeature.DrillListEvents
import com.example.drillar.presentation.DrillListFeature.DrillListStates
import com.example.drillar.presentation.DrillListFeature.DrillListViewModel
import com.example.drillar.ui.theme.DrillARTheme
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.drillar.domain.model.Drill
import com.example.drillar.presentation.core_components.AppTopBar
import com.example.drillar.R


@Composable
fun DrillListRoot(
    viewModel: DrillListViewModel = koinViewModel(),
    navigateToDetailedScreen: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DrillListScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navigateToDetailedScreen = navigateToDetailedScreen
    )
}

@Composable
fun DrillListScreen(
    state: DrillListStates,
    onEvent: (DrillListEvents) -> Unit,
    navigateToDetailedScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Select a Drill"
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(state.drillList) { drill ->
                DrillCard(
                    drill = drill,
                    onClick = navigateToDetailedScreen
                )
            }
        }
    }

}

@Preview
@Composable
private fun Preview() {
    DrillARTheme {
        DrillListScreen(
            state = DrillListStates(
                drillList = listOf(
                    Drill(
                        id = 1,
                        name = "Wall Mount Drill",
                        imageResId = R.drawable.wall_mount_drill,
                        description = "Used for mounting shelves, TVs, and decor onto walls.",
                        tips = listOf(
                            "Check for hidden wires or pipes.",
                            "Use a wall plug for secure mounting.",
                            "Start with a pilot hole."
                        )
                    ),
                    Drill(
                        id = 2,
                        name = "Woodworking Drill",
                        imageResId = R.drawable.wood_working_drill,
                        description = "Ideal for furniture making and carpentry.",
                        tips = listOf(
                            "Clamp the wood.",
                            "Use a sharp bit.",
                            "Mark drill points beforehand."
                        )
                    ),
                    Drill(
                        id = 3,
                        name = "Metal Drill",
                        imageResId = R.drawable.metal_drill,
                        description = "Used for boring through metal surfaces like steel and aluminum.",
                        tips = listOf(
                            "Use low speed.",
                            "Apply cutting oil.",
                            "Wear eye protection."
                        )
                    ),
                    Drill(
                        id = 4,
                        name = "Precision Mini Drill",
                        imageResId = R.drawable.mini_drill,
                        description = "For small crafts, jewelry, and electronic repairs.",
                        tips = listOf(
                            "Keep your hand steady.",
                            "Use it for light tasks only.",
                            "Avoid too much pressure."
                        )
                    ),
                    Drill(
                        id = 5,
                        name = "Hammer Drill",
                        imageResId = R.drawable.hammer_drill,
                        description = "Great for drilling into concrete and brick.",
                        tips = listOf(
                            "Use ear protection.",
                            "Hold it firmly.",
                            "Let the hammer do the work."
                        )
                    )
                )

            ),
            onEvent = {},
            navigateToDetailedScreen = {}
        )
    }
}