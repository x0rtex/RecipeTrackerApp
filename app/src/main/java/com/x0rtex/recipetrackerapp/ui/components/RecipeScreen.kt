package com.x0rtex.recipetrackerapp.ui.components

import androidx.annotation.StringRes
import com.x0rtex.recipetrackerapp.R

enum class RecipeScreen(@param:StringRes val title: Int) {
    Home(title = R.string.app_name),
    AddRecipe(title = R.string.recipe_add),
    ViewRecipe(title = R.string.recipe_view),
    EditRecipe(title = R.string.recipe_edit),
    Settings(title = R.string.settings),
}