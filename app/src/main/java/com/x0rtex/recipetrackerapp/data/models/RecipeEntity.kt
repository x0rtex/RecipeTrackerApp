package com.x0rtex.recipetrackerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.x0rtex.recipetrackerapp.data.database.Converters

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val ingredients: List<IngredientEntity> = emptyList(),
    val instructions: List<String> = emptyList(),
    val image: String? = null,
    val tags: List<String> = emptyList(),
    val servings: Int = 1,
    val notes: String? = null,

    val totalCalories: Double? = null,
    val totalFat: Double? = null,
    val totalSugar: Double? = null,
    val totalProtein: Double? = null,
)
