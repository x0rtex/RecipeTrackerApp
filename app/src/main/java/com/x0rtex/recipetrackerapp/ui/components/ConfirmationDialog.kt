package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.x0rtex.recipetrackerapp.R

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    confirmText: String = stringResource(id = R.string.confirm),
    cancelText: String = stringResource(id = R.string.cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
                Text(
                    modifier = modifier,
                    text = title
                )
            },
        text = {
            Text(
                modifier = modifier,
                text = message
            )
               },

        // Confirm Button
        confirmButton = {
            TextButton(
                modifier = modifier,
                onClick = onConfirm
            ) {
                Text(
                    modifier = modifier,
                    text = confirmText
                )
            }
        },

        // Cancel Button
        dismissButton = {
            TextButton(
                modifier = modifier,
                onClick = onDismiss
            ) {
                Text(
                    modifier = modifier,
                    text = cancelText
                )
            }
        }
    )
}

@Preview
@Composable
fun ConfirmationDialogPreview() {
    ConfirmationDialog(
        title = "Delete Recipe",
        message = "Are you sure you want to delete this recipe? This action cannot be undone.",
        onConfirm = {},
        onDismiss = {}
    )
}
