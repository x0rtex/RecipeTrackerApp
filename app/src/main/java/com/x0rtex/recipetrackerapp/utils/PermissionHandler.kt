package com.x0rtex.recipetrackerapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

// Camera permission states and handlers for Jetpack Compose.
@Composable
fun rememberCameraPermissionState(
    onPermissionResult: (Boolean) -> Unit
): CameraPermissionState {

    // Check if camera permission is granted
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(context.hasCameraPermission())
    }

    // Request camera permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        onPermissionResult(granted)
    }

    // Return camera permission state
    return remember {
        CameraPermissionState(
            hasPermission = hasPermission,
            requestPermission = {
                launcher.launch(Manifest.permission.CAMERA)
            }
        )
    }
}

// Camera permission state
data class CameraPermissionState(
    val hasPermission: Boolean,
    val requestPermission: () -> Unit
)

// Check if camera permission is granted
fun Context.hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

// Check if gallery permission is granted
fun Context.hasGalleryPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}
