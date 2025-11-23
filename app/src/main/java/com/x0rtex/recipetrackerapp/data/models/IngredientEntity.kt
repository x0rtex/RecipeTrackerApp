package com.x0rtex.recipetrackerapp.data.models

import androidx.room.Entity

@Entity(tableName = "ingredients")
data class IngredientEntity(
    val id: Int = 0,
    val title: String,
    val amount: Int?,
    val calories: Int?
)