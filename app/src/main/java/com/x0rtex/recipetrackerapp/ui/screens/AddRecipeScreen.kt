package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    Text(stringResource(R.string.recipe_add))
}
