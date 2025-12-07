package com.x0rtex.recipetrackerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Double? = null,
    val unit: String? = null,

    // Nutritional Info
    val calories: Double? = null,
    val fat: Double? = null,
    val sugar: Double? = null,
    val protein: Double? = null,

    // Reusability
    val isReusable: Boolean = false
)
