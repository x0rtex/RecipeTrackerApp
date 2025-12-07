package com.x0rtex.recipetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.data.models.RecipeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Suppress("EmptyMethod")
class FakeRecipeViewModel : ViewModel() {

    // Create fake recipes
    private val _uiState = MutableStateFlow(
        RecipeUiState(
            recipes = listOf(
                RecipeEntity(
                    id = 1,
                    title = "Chocolate Cake",
                    description = "A delicious chocolate cake",
                    image = "https://i.imgur.com/9zHd5ha.png",
                    ingredients = emptyList(),
                    instructions = listOf("Mix ingredients", "Bake at 350°F", "Let cool"),
                    servings = 8,
                    notes = "This is a note."
                ),
                RecipeEntity(
                    id = 2,
                    title = "Pasta Carbonara",
                    description = "Classic Italian pasta",
                    image = "https://i.imgur.com/9zHd5ha.png",
                    ingredients = emptyList(),
                    instructions = listOf("Boil pasta", "Mix sauce", "Combine and serve"),
                    servings = 4,
                    notes = "This is a note."
                ),
                RecipeEntity(
                    id = 3,
                    title = "Caesar Salad",
                    description = "Fresh and crispy salad",
                    image = "https://i.imgur.com/9zHd5ha.png",
                    ingredients = emptyList(),
                    instructions = listOf("Wash lettuce", "Make dressing", "Toss and serve"),
                    servings = 2,
                    notes = "This is a note."
                )
            ),
            selectedRecipe = RecipeEntity(
                id = 1,
                title = "Chocolate Cake",
                description = "A delicious chocolate cake",
                image = "https://i.imgur.com/9zHd5ha.png",
                ingredients = emptyList(),
                instructions = listOf("Mix ingredients", "Bake at 350°F", "Let cool"),
                servings = 8,
                notes = "This is a note."
            )
        )
    )

    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    // Dummy methods
    fun loadRecipe(id: Int) {}
    fun addRecipe(recipe: RecipeEntity) {}
    fun updateRecipe(recipe: RecipeEntity) {}
    fun deleteRecipe(recipe: RecipeEntity) {}
}