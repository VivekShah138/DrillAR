package com.example.drillar.presentation.DrillARScreen

import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.drillar.ui.theme.DrillARTheme
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.HitResult
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import org.koin.compose.viewmodel.koinViewModel

import android.Manifest
import androidx.compose.ui.unit.IntSize
import com.google.ar.core.ArCoreApk

@Composable
fun DrillARRoot(
    viewModel: DrillARViewModel = koinViewModel(),
    navigateBackToDrillList: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DrillARScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navigateBackToDrillList = navigateBackToDrillList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrillARScreen(
    state: DrillARStates,
    onEvent: (DrillAREvents) -> Unit,
    navigateBackToDrillList: () -> Unit,
) {
    val context = LocalContext.current
    val engine = rememberEngine()

    // Use state from ViewModel
    val isPlaneDetected = state.isPlaneDetected
    val isARInitialized = state.isARInitialized

    Box(modifier = Modifier.fillMaxSize()) {
        // AR Scene - Simplified version
//        ARScene(
//            modifier = Modifier.fillMaxSize(),
//            planeRenderer = true,
//            onSessionUpdated = { session, frame ->
//                // Configure session on first frame if needed
//                if (!isARInitialized) {
//                    // Configure plane detection
//                    val config = session.config
//                    config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
//                    config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
//                    session.configure(config)
//
//                    Log.d("ARInitialization", "AR initialized with config: ${config.planeFindingMode}")
//                    onEvent(DrillAREvents.OnARSessionCreated)
//                }
//
//                // Log camera tracking state
//                val camera = frame.camera
//                Log.d("ARCamera", "Camera tracking state: ${camera.trackingState}")
//
//                // Only check for planes if camera is tracking
//                if (camera.trackingState == com.google.ar.core.TrackingState.TRACKING) {
//                    val planes = session.getAllTrackables(Plane::class.java)
//                    Log.d("PlaneDetection", "Total planes: ${planes.size}")
//
//                    val trackingPlanes = planes.filter {
//                        it.trackingState == com.google.ar.core.TrackingState.TRACKING
//                    }
//
//                    if (trackingPlanes.isNotEmpty() && !isPlaneDetected) {
//                        Log.d("PlaneDetection", "Planes detected: ${trackingPlanes.size}")
//                        onEvent(DrillAREvents.OnPlaneDetected)
//                    } else if (planes.isEmpty()) {
//                        Log.d("PlaneDetection", "No planes found")
//                    }
//                } else {
//                    Log.d("PlaneDetection", "Camera not tracking yet: ${camera.trackingState}")
//                }
//            }
//        )

        ARScene(
            modifier = Modifier.fillMaxSize(),
            planeRenderer = true,
            onSessionUpdated = { session, frame ->
                if (!isARInitialized) {
                    val config = session.config
                    config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
                    config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                    session.configure(config)

                    Log.d("ARInitialization", "AR initialized with config: ${config.planeFindingMode}")
                    onEvent(DrillAREvents.OnARSessionCreated)
                }

                val camera = frame.camera

                if (camera.trackingState == com.google.ar.core.TrackingState.TRACKING) {
                    val planes = session.getAllTrackables(Plane::class.java)

                    // More detailed logging
                    Log.d("PlaneDebug", "=== Frame Debug ===")
                    Log.d("PlaneDebug", "Total planes found: ${planes.size}")
                    Log.d("PlaneDebug", "Camera pose: ${camera.pose}")
                    Log.d("PlaneDebug", "Frame timestamp: ${frame.timestamp}")

                    // Log session statistics
                    val allAnchors = session.getAllAnchors()
                    Log.d("PlaneDebug", "Total anchors: ${allAnchors.size}")

                    planes.forEachIndexed { index, plane ->
                        Log.d("PlaneDebug", "Plane $index: type=${plane.type}, state=${plane.trackingState}")
                    }
                } else {
                    Log.d("CameraState", "Camera not tracking: ${camera.trackingState}")
                }
            }
        )

        // Top UI Overlay
        TopAppBar(
            title = {
                Text(
                    text = "Place ${state.selectedDrillName}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = navigateBackToDrillList
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.7f)
            )
        )

        // Bottom Instructions Overlay
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.8f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (!state.isPlaneDetected) {
                        "Move your device to detect the ground plane"
                    } else if (state.isDrillPlaced) {
                        "Drill marker placed! Tap 'Remove' to place elsewhere"
                    } else {
                        "Tap 'Place Drill' to place your ${state.selectedDrillName}"
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Status indicators
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusIndicator(
                        label = "AR Ready",
                        isActive = state.isARInitialized
                    )
                    StatusIndicator(
                        label = "Plane Detection",
                        isActive = state.isPlaneDetected
                    )
                    StatusIndicator(
                        label = "Drill Placed",
                        isActive = state.isDrillPlaced
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.isPlaneDetected && !state.isDrillPlaced) {
                        Button(
                            onClick = {
                                // Simulate placing drill - just trigger the event
                                onEvent(DrillAREvents.OnDrillPlaced(floatArrayOf(0f, 0f, -1f)))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Place Drill Here")
                        }
                    }

                    if (state.isDrillPlaced) {
                        Button(
                            onClick = {
                                onEvent(DrillAREvents.OnDrillRemoved)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Remove Drill")
                        }
                    }

                    if (state.errorMessage != null) {
                        Button(
                            onClick = {
                                onEvent(DrillAREvents.OnRetry)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // AR Session status
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.8f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Initializing AR...",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Error state
        state.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Visual drill marker indicator (when drill is placed)
        if (state.isDrillPlaced) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                // Outer ring
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = when (state.selectedDrillId) {
                                1 -> Color.Red.copy(alpha = 0.3f)
                                2 -> Color.Green.copy(alpha = 0.3f)
                                3 -> Color.Blue.copy(alpha = 0.3f)
                                else -> Color.Gray.copy(alpha = 0.3f)
                            },
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )

                // Inner circle
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = when (state.selectedDrillId) {
                                1 -> Color.Red.copy(alpha = 0.8f)
                                2 -> Color.Green.copy(alpha = 0.8f)
                                3 -> Color.Blue.copy(alpha = 0.8f)
                                else -> Color.Gray.copy(alpha = 0.8f)
                            },
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔧",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }

        // Drill info overlay
        if (state.isDrillPlaced) {
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (state.selectedDrillId) {
                        1 -> Color.Red.copy(alpha = 0.9f)
                        2 -> Color.Green.copy(alpha = 0.9f)
                        3 -> Color.Blue.copy(alpha = 0.9f)
                        else -> Color.Gray.copy(alpha = 0.9f)
                    }
                )
            ) {
                Text(
                    text = "${state.selectedDrillName} Placed Successfully!",
                    modifier = Modifier.padding(12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrillARScreen2(
    state: DrillARStates,
    onEvent: (DrillAREvents) -> Unit,
    navigateBackToDrillList: () -> Unit,
) {
    val context = LocalContext.current
    val engine = rememberEngine()
    var currentSession by remember { mutableStateOf<Session?>(null) }
    var currentFrame by remember { mutableStateOf<Frame?>(null) }


    // Use state from ViewModel
    val isPlaneDetected = state.isPlaneDetected
    val isARInitialized = state.isARInitialized

    var size by remember { mutableStateOf(IntSize.Zero) }


    // Store the current AR session and frame for hit testing
    var hasPermission by remember { mutableStateOf(false) }

    var isSessionConfigured by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (!hasPermission) {
        Log.d("ARPlaneDetection2", "Camera permission is required to use AR features.")
    } else {

        Log.d("ARPlaneDetection2", "Camera permission is granted.")
        val arAvailability = ArCoreApk.getInstance().checkAvailability(context)
        Log.d("ARPlaneDetection2", "AR Availability: $arAvailability")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // AR Scene with touch handling
        ARScene(
            modifier = Modifier
                .fillMaxSize(),
//                .pointerInput(Unit) {
//                    detectTapGestures { offset ->
//
//                        Log.d("ARTap", "isPlaneDetected $isPlaneDetected")
//                        Log.d("ARTap", "isDrillPlaced ${state.isDrillPlaced}")
//                        Log.d("ARTap", "currentSession ${currentSession != null}")
//                        Log.d("ARTap", "currentFrame ${currentFrame != null}")
//
//                        // Handle tap for drill placement
//                        Log.d("ARTap", "Tapped at $offset, screen size: $size")
//                        if (size == IntSize.Zero) {
//                            Log.d("ARTap", "Screen size not ready")
//                            return@detectTapGestures
//                        }
//                        if (isPlaneDetected && !state.isDrillPlaced && currentSession != null && currentFrame != null) {
//                            performHitTest(
//                                session = currentSession!!,
//                                frame = currentFrame!!,
//                                tapX = offset.x,
//                                tapY = offset.y,
//                                screenWidth = size.width,
//                                screenHeight = size.height,
//                                onHitResult = { hitResult ->
//                                    // Extract pose from hit result
//                                    val pose = hitResult.hitPose
//                                    val position = floatArrayOf(
//                                        pose.tx(),
//                                        pose.ty(),
//                                        pose.tz()
//                                    )
//                                    onEvent(DrillAREvents.OnDrillPlaced(position))
//                                }
//                            )
//                        } else {
//                            Log.d("ARTap", "Error in performing hit test")
//                        }
//                    }
//                },
            planeRenderer = true,
            onSessionUpdated = { session, frame ->

                Log.d("ARSession", "Session: $session")
                Log.d("ARSession", "Camera tracking state: ${frame.camera.trackingState}")

                // Configure session only once
                if (!isSessionConfigured) {
                    val config = Config(session).apply {
                        planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
                    }
                    session.configure(config)
                    isSessionConfigured = true
                    Log.d("ARSessionConfig", "Session configured for horizontal and vertical plane detection")
                }


                if (frame.camera.trackingState == TrackingState.TRACKING) {
                    if (!isARInitialized) {
                        onEvent(DrillAREvents.OnARSessionCreated)
                    }
                    try {
                        val planes = session.getAllTrackables(Plane::class.java)
                        planes.forEach { plane ->
                            Log.d(
                                "ARPlaneDetection",
                                "Plane type: ${plane.type}, Tracking: ${plane.trackingState}"
                            )
                        }

                        val hasTrackingPlanes = planes.any {
                            it.trackingState == TrackingState.TRACKING
                        }

                        val trackingPlanes = planes.filter { plane ->
                            plane.trackingState == TrackingState.TRACKING &&
                                    plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING
                        }

                        Log.d("ARPlaneDetection", "Horizontal Planes: $trackingPlanes")

                        if (hasTrackingPlanes && !isPlaneDetected) {
                            Log.d("ARPlaneDetection", "Plane detected!")
//                            onEvent(DrillAREvents.OnPlaneDetected)
                        }
                    } catch (e: Exception) {
                        Log.e("ARPlaneDetection", "Error in plane detection: ${e.message}")
                    }
                } else {
                    Log.d("ARPlaneDetection", "Camera not tracking")
                }


                val planes = session.getAllTrackables(Plane::class.java)
                planes.forEach { plane ->
                    Log.d("ARPlane", "Type: ${plane.type}, Tracking: ${plane.trackingState}")
                }
            }


        )

        // Top UI Overlay
        TopAppBar(
            title = {
                Text(
                    text = "Place ${state.selectedDrillName}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = navigateBackToDrillList
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.7f)
            )
        )

        // Bottom Instructions Overlay
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.8f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = when {
                        !state.isARInitialized -> "Initializing AR camera..."
                        !state.isPlaneDetected -> "Move your device slowly to detect the ground plane"
                        state.isDrillPlaced -> "Drill marker placed! Tap 'Remove' to place elsewhere"
                        else -> "Tap on the detected plane to place your ${state.selectedDrillName}"
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Status indicators
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusIndicator(
                        label = "AR Ready",
                        isActive = state.isARInitialized
                    )
                    StatusIndicator(
                        label = "Plane Detection",
                        isActive = state.isPlaneDetected
                    )
                    StatusIndicator(
                        label = "Drill Placed",
                        isActive = state.isDrillPlaced
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.isDrillPlaced) {
                        Button(
                            onClick = {
                                onEvent(DrillAREvents.OnDrillRemoved)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Remove Drill")
                        }
                    }

                    if (state.errorMessage != null) {
                        Button(
                            onClick = {
                                onEvent(DrillAREvents.OnRetry)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // AR Session status
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.8f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Initializing AR...",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Error state
        state.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Visual drill marker indicator (when drill is placed)
        if (state.isDrillPlaced) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                // Outer ring with pulsing animation
                val infiniteTransition = rememberInfiniteTransition()
                val pulseAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.3f,
                    targetValue = 0.7f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = when (state.selectedDrillId) {
                                1 -> Color.Red.copy(alpha = pulseAlpha)
                                2 -> Color.Green.copy(alpha = pulseAlpha)
                                3 -> Color.Blue.copy(alpha = pulseAlpha)
                                else -> Color.Gray.copy(alpha = pulseAlpha)
                            },
                            shape = CircleShape
                        )
                )

                // Inner circle
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = when (state.selectedDrillId) {
                                1 -> Color.Red.copy(alpha = 0.8f)
                                2 -> Color.Green.copy(alpha = 0.8f)
                                3 -> Color.Blue.copy(alpha = 0.8f)
                                else -> Color.Gray.copy(alpha = 0.8f)
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔧",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }

        // Drill info overlay
        if (state.isDrillPlaced) {
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (state.selectedDrillId) {
                        1 -> Color.Red.copy(alpha = 0.9f)
                        2 -> Color.Green.copy(alpha = 0.9f)
                        3 -> Color.Blue.copy(alpha = 0.9f)
                        else -> Color.Gray.copy(alpha = 0.9f)
                    }
                )
            ) {
                Text(
                    text = "${state.selectedDrillName} Placed Successfully!",
                    modifier = Modifier.padding(12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Crosshair for targeting when plane is detected but drill not placed
        if (state.isPlaneDetected && !state.isDrillPlaced) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                // Horizontal line
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .background(Color.White.copy(alpha = 0.7f))
                )
                // Vertical line
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(Color.White.copy(alpha = 0.7f))
                )
            }
        }
    }
}


//@Composable
//fun DrillARScreen(
//    state: DrillARStates,
//    onEvent: (DrillAREvents) -> Unit,
//    navigateBackToDrillList: () -> Unit,
//) {
//    var isSessionConfigured by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        isSessionConfigured = false
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        ARScene(
//            modifier = Modifier.fillMaxSize(),
//            planeRenderer = true,
//            onSessionUpdated = { session, frame ->
//
//                Log.d("ARSession", "Session: $session")
//                Log.d("ARSession", "Camera tracking state: ${frame.camera.trackingState}")
//
//                if (!isSessionConfigured) {
//                    try {
//                        val config = Config(session).apply {
//                            planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
//                        }
//                        session.configure(config)
//                        isSessionConfigured = true
//                        Log.d("ARSessionConfig", "Session configured for plane detection")
//                    } catch (e: Exception) {
//                        Log.e("ARSessionConfig", "Error configuring session: ${e.message}")
//                    }
//                }
//
//                if (frame.camera.trackingState == TrackingState.TRACKING) {
//                    try {
//                        val planes = session.getAllTrackables(Plane::class.java)
//                        Log.d("ARPlaneDetection", "Plane count: ${planes.size}")
//
//                        planes.forEach { plane ->
//                            Log.d(
//                                "ARPlaneDetection",
//                                "Plane type: ${plane.type}, Plane Tracking State: ${plane.trackingState}"
//                            )
//                        }
//                    } catch (e: Exception) {
//                        Log.e("ARPlaneDetection", "Error in plane detection: ${e.message}")
//                    }
//                } else {
//                    Log.d("ARSession", "Camera tracking state is set to: ${frame.camera.trackingState}")
//                }
//            }
//        )
//    }
//}
//


// Helper function for hit testing
private fun performHitTest(
    session: Session,
    frame: Frame,
    tapX: Float,
    tapY: Float,
    screenWidth: Int,
    screenHeight: Int,
    onHitResult: (HitResult) -> Unit
) {
    try {
        // Convert screen coordinates to normalized coordinates
        val normalizedX = tapX / screenWidth
        val normalizedY = tapY / screenHeight

        // Perform hit test
        val hitResults = frame.hitTest(tapX, tapY)

        // Find the closest hit result on a plane
        val planeHitResult = hitResults
            .filter { hitResult ->
                val trackable = hitResult.trackable
                trackable is Plane && trackable.isPoseInPolygon(hitResult.hitPose)
            }
            .minByOrNull { it.distance }

        planeHitResult?.let { hitResult ->
            onHitResult(hitResult)
        }
    } catch (e: Exception) {
        Log.e("DrillAR", "Error in hit test: ${e.message}")
    }
}

@Composable
private fun StatusIndicator(
    label: String,
    isActive: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = if (isActive) Color.Green else Color.Gray,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DrillARTheme {
        DrillARScreen(
            state = DrillARStates(
                selectedDrillId = 1,
                selectedDrillName = "Drill 1",
                isLoading = false,
                isDrillPlaced = false
            ),
            onEvent = {},
            navigateBackToDrillList = {}
        )
    }
}