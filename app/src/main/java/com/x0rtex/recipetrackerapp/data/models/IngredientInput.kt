package com.x0rtex.recipetrackerapp.data.models

data class IngredientInput(
    val name: String = "",
    val amount: String = "",
    val unit: String = ""
) {
    fun toIngredientEntity(): IngredientEntity {
        return IngredientEntity(
            name = name.trim(),
            amount = amount.toDoubleOrNull(),
            unit = unit.trim().takeIf { it.isNotEmpty() }
        )
    }
}