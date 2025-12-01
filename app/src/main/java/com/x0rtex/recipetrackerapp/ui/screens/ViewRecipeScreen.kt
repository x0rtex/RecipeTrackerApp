package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity

@Composable
fun ViewRecipeScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity?,
    onEditClick: () -> Unit,
) {
    if (recipe == null) {
        Text("Recipe not found")
    } else {
        Column(modifier = modifier) {
            Text("Title: ${recipe.title}")
            Text("Description: ${recipe.description}")
            Text("Servings: ${recipe.servings}")
            // TODO: Add buttons for edit/delete
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewRecipeScreenPreview() {
    ViewRecipeScreen(
        recipe = RecipeEntity(
            id = 1,
            title = "Chocolate Cake",
            description = "A delicious chocolate cake recipe",
            ingredients = emptyList(),
            instructions = listOf("Preheat oven", "Mix ingredients", "Bake for 30 minutes"),
            servings = 8
        ),
        onEditClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ViewRecipeScreenEmptyPreview() {
    ViewRecipeScreen(
        recipe = null,
        onEditClick = {},
    )
}