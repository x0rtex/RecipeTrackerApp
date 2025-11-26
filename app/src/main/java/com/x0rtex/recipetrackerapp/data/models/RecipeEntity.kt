package com.x0rtex.recipetrackerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val ingredients: List<IngredientEntity>,
    val instructions: List<String>,
    val image: String? = null,
    val servings: Int,
)