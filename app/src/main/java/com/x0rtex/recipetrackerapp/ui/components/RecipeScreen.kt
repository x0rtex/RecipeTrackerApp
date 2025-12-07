package com.x0rtex.recipetrackerapp.ui.components

import androidx.annotation.StringRes
import com.x0rtex.recipetrackerapp.R

enum class RecipeScreen(@param:StringRes val title: Int) {
    Onboarding(title = R.string.onboarding),
    Home(title = R.string.app_name),
    AddRecipe(title = R.string.add_recipe),
    ViewRecipe(title = R.string.view_recipe),
    EditRecipe(title = R.string.edit_recipe),
    Settings(title = R.string.settings),
}