package com.x0rtex.recipetrackerapp.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.ui.screens.CameraScreen
import com.x0rtex.recipetrackerapp.utils.rememberCameraPermissionState

// Image picker with camera and gallery selection
@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(value = true) }
    var showCamera by remember { mutableStateOf(value = false) }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onImageSelected(it)
        } ?: run {
            onDismiss()
        }
    }

    // Camera permission handler
    val cameraPermissionState = rememberCameraPermissionState { granted ->
        if (granted) {
            showCamera = true
            showDialog = false
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.camera_permission_required),
                Toast.LENGTH_SHORT
            ).show()
            onDismiss()
        }
    }

    // Show picker dialog
    if (showDialog) {
        ImagePickerDialog(
            onDismiss = onDismiss,
            onCameraClick = {
                showDialog = false
                if (cameraPermissionState.hasPermission) {
                    showCamera = true
                } else {
                    cameraPermissionState.requestPermission()
                }
            },
            onGalleryClick = {
                showDialog = false
                galleryLauncher.launch("image/*")
            }
        )
    }

    // Show camera screen
    if (showCamera) {
        CameraScreen(
            onImageCaptured = { uri ->
                onImageSelected(uri)
                showCamera = false
            },
            onError = { exception ->
                Toast.makeText(
                    context,
                    context.getString(R.string.photo_capture_failed, exception.message),
                    Toast.LENGTH_SHORT
                ).show()
                onDismiss()
            },
            onDismiss = {
                showCamera = false
                onDismiss()
            }
        )
    }
}
