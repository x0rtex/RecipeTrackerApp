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

    // Recipe Repository
    private val repo = RecipeRepository(application.applicationContext)

    // Track recipes
    private val _recipes = MutableStateFlow<List<RecipeEntity>>(value = emptyList())
    val recipes: StateFlow<List<RecipeEntity>> = _recipes.asStateFlow()

    // Track selected recipe
    private val _selectedRecipe = MutableStateFlow<RecipeEntity?>(value = null)
    val selectedRecipe: StateFlow<RecipeEntity?> = _selectedRecipe.asStateFlow()

    // Track search query
    private val _searchQuery = MutableStateFlow(value = "")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Track selected tag
    private val _selectedTag = MutableStateFlow<String?>(value = null)
    val selectedTag: StateFlow<String?> = _selectedTag.asStateFlow()

    // Track all available tags from recipes
    private val _availableTags = MutableStateFlow<List<String>>(value = emptyList())
    val availableTags: StateFlow<List<String>> = _availableTags.asStateFlow()

    // Filtered recipes based on search and tag
    val filteredRecipes: StateFlow<List<RecipeEntity>> = combine(
        flow = _recipes,
        flow2 = _searchQuery,
        flow3 = _selectedTag
    ) { recipes, query, tag ->
        recipes
            .filter { recipe ->
                // Filter by recipe title
                val matchesSearch = query.isBlank() || recipe.title.contains(other = query, ignoreCase = true)

                // Filter by tag
                val matchesTag = tag == null || recipe.tags.contains(tag)

                matchesSearch && matchesTag
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = emptyList()
    )

    // Combine states into UI state
    val uiState: StateFlow<RecipeUiState> = combine(
        flow = filteredRecipes,
        flow2 = _selectedRecipe
    ) { recipes, selectedRecipe ->
        RecipeUiState(
            recipes = recipes,
            selectedRecipe = selectedRecipe
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = RecipeUiState()
    )

    // Load all recipes on initialisation
    init {
        loadAllRecipes()
    }

    // Load all recipes from repository
    fun loadAllRecipes() {
        viewModelScope.launch {
            repo.getAllRecipes().collect { recipeList ->
                _recipes.value = recipeList

                // Extract unique tags from all recipes
                val tags = recipeList
                    .flatMap { it.tags }
                    .distinct()
                    .sorted()
                _availableTags.value = tags
            }
        }
    }

    // Update search query to filter recipes
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Select a tag to filter recipes
    fun selectTag(tag: String?) {
        _selectedTag.value = tag
    }

    // Load recipe by ID from repository
    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            _selectedRecipe.value = repo.getRecipe(id)
        }
    }

    // Add recipe to repository
    fun addRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.insertRecipe(recipe)
        }
    }

    // Update recipe to repository
    fun updateRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.updateRecipe(recipe)
            _selectedRecipe.value = repo.getRecipe(recipe.id)
        }
    }

    // Delete recipe from repository
    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.deleteRecipe(recipe)
        }
    }
}
