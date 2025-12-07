package com.x0rtex.recipetrackerapp.data.repository

import android.content.Context
import com.x0rtex.recipetrackerapp.data.database.RecipeDatabase
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepository(
    context: Context
) {
    // Get all recipes from the database
    private val recipeDao = RecipeDatabase.get(context).recipeDao()

    fun getAllRecipes(): Flow<List<RecipeEntity>> = recipeDao.getAll()
    suspend fun getRecipe(id: Int) = recipeDao.getRecipe(id)
    suspend fun insertRecipe(recipe: RecipeEntity) = recipeDao.insert(recipe)
    suspend fun updateRecipe(recipe: RecipeEntity) = recipeDao.update(recipe)
    suspend fun deleteRecipe(recipe: RecipeEntity) = recipeDao.delete(recipe)
}