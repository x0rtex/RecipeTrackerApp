package com.x0rtex.recipetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.data.models.RecipeUiState
import com.x0rtex.recipetrackerapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repo = RecipeRepository(application.applicationContext)

    private val _recipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val recipes: StateFlow<List<RecipeEntity>> = _recipes.asStateFlow()

    private val _selectedRecipe = MutableStateFlow<RecipeEntity?>(null)
    val selectedRecipe: StateFlow<RecipeEntity?> = _selectedRecipe.asStateFlow()

    val uiState: StateFlow<RecipeUiState> = combine(
        _recipes,
        _selectedRecipe
    ) { recipes, selectedRecipe ->
        RecipeUiState(
            recipes = recipes,
            selectedRecipe = selectedRecipe
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RecipeUiState()
    )

    init {
        loadAllRecipes()
    }

    fun loadAllRecipes() {
        viewModelScope.launch {
            repo.getAllRecipes().collect {
                _recipes.value = it
            }
        }
    }

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            _selectedRecipe.value = repo.getRecipe(id)
        }
    }

    fun addRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.insertRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.deleteRecipe(recipe)
        }
    }
}
