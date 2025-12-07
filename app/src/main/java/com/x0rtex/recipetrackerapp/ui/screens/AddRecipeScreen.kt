package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.ui.components.ImagePicker
import com.x0rtex.recipetrackerapp.ui.components.RecipeFormContent
import com.x0rtex.recipetrackerapp.ui.components.rememberRecipeFormState
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme

@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    onSaveClick: (RecipeEntity) -> Unit
) {
    val formState = rememberRecipeFormState()
    var showImagePicker by remember { mutableStateOf(value = false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (formState.validate()) {
                        onSaveClick(formState.toRecipe())
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                Spacer(Modifier.width(width = 8.dp))
                Text(text = stringResource(id = R.string.save_recipe))
            }
        }
    ) { paddingValues ->
        RecipeFormContent(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            formState = formState,
            onAddPhotoClick = { showImagePicker = true }
        )
    }

    // Show Image Picker When Triggered
    if (showImagePicker) {
        ImagePicker(
            onImageSelected = { uri ->
                formState.imageUri = uri.toString()
                showImagePicker = false
            },
            onDismiss = { showImagePicker = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    RecipeTrackerAppTheme {
        AddRecipeScreen(onSaveClick = {})
    }
}
