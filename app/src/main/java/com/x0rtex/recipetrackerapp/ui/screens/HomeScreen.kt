package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    Column(
        modifier = modifier
    ) {
        val recipes by viewModel.recipes.collectAsState()

        LazyColumn {
            items(recipes) { recipe ->
                Text(recipe.title)
            }
        }
    }
}
