package com.x0rtex.recipetrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    // Get All Recipes
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): Flow<List<RecipeEntity>>

    // Get Recipe By Id
    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    suspend fun getRecipe(id: Int): RecipeEntity?

    // Add Recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity)

    // Update Recipe
    @Update
    suspend fun update(recipe: RecipeEntity)

    // Delete Recipe
    @Delete
    suspend fun delete(recipe: RecipeEntity)

    // Get Recipe Count
    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int

    // Get Recipes Without Images
    @Query("SELECT * FROM recipes WHERE image IS NULL")
    suspend fun getRecipesWithoutImages(): List<RecipeEntity>

    // Get Recipes By Title
    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>
}