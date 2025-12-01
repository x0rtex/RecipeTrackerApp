package com.x0rtex.recipetrackerapp.data.models

data class RecipeUiState(
    val recipes: List<RecipeEntity> = emptyList(),
    val selectedRecipe: RecipeEntity? = null
)