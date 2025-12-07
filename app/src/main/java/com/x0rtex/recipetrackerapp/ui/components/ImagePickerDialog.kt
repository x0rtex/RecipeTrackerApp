package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.x0rtex.recipetrackerapp.R

@Composable
fun ImagePickerDialog(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.select_image_source)) },
        text = { Text(text = stringResource(id = R.string.choose_where_to_get_your_photo_from)) },
        confirmButton = {
            TextButton(onClick = onCameraClick) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
                Text(text = stringResource(id = R.string._camera))
            }
        },
        dismissButton = {
            TextButton(onClick = onGalleryClick) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
                Text(text = stringResource(id = R.string._gallery))
            }
        }
    )
}
