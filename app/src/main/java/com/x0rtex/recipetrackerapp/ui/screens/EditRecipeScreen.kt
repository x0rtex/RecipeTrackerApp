package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity

@Composable
fun EditRecipeScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity?,
    onSaveClick: (RecipeEntity) -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: (RecipeEntity) -> Unit
) {
    if (recipe == null) {
        Text("Recipe not found")
        return
    }

    Scaffold(
        // Floating Delete Recipe Button
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onDeleteClick(recipe) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.recipe_delete)
                )
            }
        }
    ) { innerPadding ->
        // TODO: Implement edit form with recipe data pre-filled
        Text(stringResource(R.string.recipe_edit))
    }
}

// Preview with fake data!
@Preview(showBackground = true)
@Composable
fun EditRecipeScreenPreview() {
    EditRecipeScreen(
        recipe = RecipeEntity(
            id = 1,
            title = "Chocolate Cake",
            description = "A delicious chocolate cake recipe",
            ingredients = emptyList(),
            instructions = listOf("Preheat oven", "Mix ingredients", "Bake for 30 minutes"),
            servings = 8
        ),
        onSaveClick = {},
        onCancelClick = {},
        onDeleteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditRecipeScreenEmptyPreview() {
    EditRecipeScreen(
        recipe = null,
        onSaveClick = {},
        onCancelClick = {},
        onDeleteClick = {}
    )
}