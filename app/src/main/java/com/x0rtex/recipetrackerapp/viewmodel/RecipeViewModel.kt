package com.x0rtex.recipetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel() : ViewModel() {
    private val _recipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val recipes: StateFlow<List<RecipeEntity>> = _recipes.asStateFlow()
}
