package com.example.drillar.presentation.DrillDetailsScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drillar.presentation.DrillDetailsScreen.DrillDetailsEvents
import com.example.drillar.presentation.DrillDetailsScreen.DrillDetailsStates
import com.example.drillar.presentation.DrillDetailsScreen.DrillDetailsViewModel
import com.example.drillar.ui.theme.DrillARTheme
import org.koin.compose.viewmodel.koinViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drillar.domain.model.Drill
import com.example.drillar.presentation.core_components.AppTopBar


@Composable
fun DrillDetailsRoot(
    viewModel: DrillDetailsViewModel = koinViewModel(),
    navigateBackToDrillList: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DrillDetailsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navigateBackToDrillList = navigateBackToDrillList
    )
}

//@Composable
//fun DrillDetailsScreen(
//    state: DrillDetailsStates,
//    onEvent: (DrillDetailsEvents) -> Unit,
//    navigateBackToDrillList: () -> Unit,
//) {
//
//    val context = LocalContext.current
//
//    Scaffold(
//        topBar = {
//
//            AppTopBar(
//                title = state.name,
//                showBackButton = true,
//                onBackClick = navigateBackToDrillList
//            )
//
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//                .fillMaxSize()
//        ) {
//            if (state.imageResId != 0) {
//                Image(
//                    painter = painterResource(id = state.imageResId),
//                    contentDescription = state.name,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text("Description", fontWeight = FontWeight.Bold)
//            Text(state.description)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text("Tips", fontWeight = FontWeight.Bold)
//            state.tips.forEach { tip ->
//                Text("• $tip")
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(
//                onClick = {
//                    Toast.makeText(context, "AR Screen Coming Soon", Toast.LENGTH_SHORT).show()
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Start AR Drill")
//            }
//        }
//    }
//}

@Composable
fun DrillDetailsScreen(
    state: DrillDetailsStates,
    onEvent: (DrillDetailsEvents) -> Unit,
    navigateBackToDrillList: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = state.name,
                showBackButton = true,
                onBackClick = navigateBackToDrillList
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                if (state.imageResId != 0) {
                    Image(
                        painter = painterResource(id = state.imageResId),
                        contentDescription = state.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Description", fontWeight = FontWeight.Bold)
                Text(state.description)

                Spacer(modifier = Modifier.height(12.dp))

                Text("Tips", fontWeight = FontWeight.Bold)
                state.tips.forEach { tip ->
                    Text("• $tip")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "AR Screen Coming Soon", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start AR Drill")
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    DrillARTheme  {
        DrillDetailsScreen(
            state = DrillDetailsStates(),
            onEvent = {},
            navigateBackToDrillList = {}
        )
    }
}