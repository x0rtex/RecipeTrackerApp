package com.x0rtex.recipetrackerapp.data.models

data class RecipeEntity(
    val id: Int = 0,
    val title: String,
    val description: String,
    val ingredients: MutableList<IngredientEntity>,
    val instructions: MutableList<String>,
    val image: Int,
    val servings: Int,
)