package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.x0rtex.recipetrackerapp.ui.components.RecipeCard
import com.x0rtex.recipetrackerapp.ui.components.RecipeScreen
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    val recipes by viewModel.recipes.collectAsState()

    Column(
        modifier = modifier
    ) {
        // TODO: Search bar

        if (recipes.isEmpty()) {
            Text(
                text = "No recipes yet. Add your first one!"
            )
        } else {
            LazyColumn {
                items(recipes) { recipe ->
                    RecipeCard(
                        modifier = Modifier.padding(bottom = 8.dp),
                        recipe = recipe,
                        onClick = {
                            viewModel.loadRecipe(recipe.id)
                            navController.navigate(RecipeScreen.ViewRecipe.name)
                        }
                    )
                }
            }
        }
    }
}
