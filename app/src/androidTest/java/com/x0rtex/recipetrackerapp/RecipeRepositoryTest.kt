package com.x0rtex.recipetrackerapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// Tests CRUD operations and data persistence.
@RunWith(AndroidJUnit4::class)
class RecipeRepositoryTest {

    private lateinit var repository: RecipeRepository
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        repository = RecipeRepository(context)
    }

    @Test
    fun insertAndRetrieveRecipe() = runBlocking {
        val recipe = RecipeEntity(
            title = "Test Cake",
            description = "A test recipe",
            servings = 4,
            ingredients = listOf(
                IngredientEntity(name = "Flour", amount = 200.0, unit = "g")
            ),
            instructions = listOf("Mix", "Bake"),
            tags = listOf("Test", "Dessert")
        )

        repository.insertRecipe(recipe)
        val recipes = repository.getAllRecipes().first()

        assertTrue(recipes.any { it.title == "Test Cake" })
        val retrieved = recipes.first { it.title == "Test Cake" }
        assertEquals("A test recipe", retrieved.description)
        assertEquals(4, retrieved.servings)
        assertEquals(1, retrieved.ingredients.size)
    }

    @Test
    fun updateRecipe() = runBlocking {
        val recipe = RecipeEntity(
            title = "Original Title",
            servings = 2
        )

        repository.insertRecipe(recipe)
        val recipes = repository.getAllRecipes().first()
        val inserted = recipes.first { it.title == "Original Title" }

        val updated = inserted.copy(title = "Updated Title", servings = 6)
        repository.updateRecipe(updated)

        val afterUpdate = repository.getRecipe(inserted.id)
        assertEquals("Updated Title", afterUpdate?.title)
        assertEquals(6, afterUpdate?.servings)
    }

    @Test
    fun deleteRecipe() = runBlocking {
        val recipe = RecipeEntity(
            title = "To Be Deleted",
            servings = 1
        )

        repository.insertRecipe(recipe)
        val recipes = repository.getAllRecipes().first()
        val inserted = recipes.first { it.title == "To Be Deleted" }

        repository.deleteRecipe(inserted)

        val afterDelete = repository.getRecipe(inserted.id)
        assertNull(afterDelete)
    }

    @Test
    fun getRecipeById() = runBlocking {
        val recipe = RecipeEntity(
            title = "Find Me",
            servings = 3
        )

        repository.insertRecipe(recipe)
        val allRecipes = repository.getAllRecipes().first()
        val inserted = allRecipes.first { it.title == "Find Me" }

        val found = repository.getRecipe(inserted.id)

        assertNotNull(found)
        assertEquals("Find Me", found?.title)
        assertEquals(3, found?.servings)
    }
}
