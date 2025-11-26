package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun ViewRecipeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel,
    recipeId: Int? = 0
) {
    Scaffold(
        // Floating Edit Recipe Button
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(id = R.string.recipe_edit)
                )
            }
        }
    ) { _ ->
        Text(stringResource(R.string.recipe_view))
    }
}
