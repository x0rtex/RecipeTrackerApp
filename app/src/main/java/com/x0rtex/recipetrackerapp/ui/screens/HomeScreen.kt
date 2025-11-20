package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(viewModel: RecipeViewModel) {
    Text(stringResource(R.string.app_name))
}

@Preview
@Composable
fun HomeScreenPreview() {
    val viewModel = RecipeViewModel()
    HomeScreen(viewModel)
}