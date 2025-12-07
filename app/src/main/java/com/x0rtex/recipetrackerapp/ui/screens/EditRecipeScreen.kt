package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.ui.components.ConfirmationDialog
import com.x0rtex.recipetrackerapp.ui.components.ImagePicker
import com.x0rtex.recipetrackerapp.ui.components.RecipeFormContent
import com.x0rtex.recipetrackerapp.ui.components.rememberRecipeFormState
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme

@Composable
fun EditRecipeScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity?,
    onSaveClick: (RecipeEntity) -> Unit,
    onDeleteClick: (RecipeEntity) -> Unit
) {
    // Check if recipe exists
    if (recipe == null) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(text = stringResource(id = R.string.recipe_not_found))
        }
        return
    }

    // Initialize form state with existing recipe
    val formState = rememberRecipeFormState(recipe)
    var showDeleteDialog by remember { mutableStateOf(value = false) }
    var showImagePicker by remember { mutableStateOf(value = false) }

    // Show Delete Dialog When Triggered
    if (showDeleteDialog) {
        ConfirmationDialog(
            title = stringResource(id = R.string.delete_recipe),
            message = stringResource(id = R.string.delete_recipe_confirmation, recipe.title),
            confirmText = stringResource(id = R.string.delete),
            cancelText = stringResource(id = R.string.cancel),
            onConfirm = {
                onDeleteClick(recipe)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        floatingActionButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(space = 16.dp)) {
                // Delete button
                FloatingActionButton(
                    onClick = { showDeleteDialog = true },
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.delete_recipe)
                    )
                }

                // Save button
                ExtendedFloatingActionButton(
                    onClick = {
                        if (formState.validate()) {
                            onSaveClick(formState.toUpdatedRecipe(originalRecipe = recipe))
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                    Spacer(Modifier.width(width = 8.dp))
                    Text(text = stringResource(id = R.string.save_changes))
                }
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
fun EditRecipeScreenPreview() {
    RecipeTrackerAppTheme {
        EditRecipeScreen(
            recipe = RecipeEntity(
                id = 1,
                title = "Chocolate Cake",
                description = "A delicious chocolate cake recipe",
                tags = listOf("Dessert", "Baking"),
                ingredients = listOf(
                    IngredientEntity(name = "Flour", amount = 200.0, unit = "g"),
                    IngredientEntity(name = "Sugar", amount = 150.0, unit = "g")
                ),
                instructions = listOf("Preheat oven", "Mix ingredients", "Bake for 30 minutes"),
                servings = 8,
                notes = "This is a note."
            ),
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}
