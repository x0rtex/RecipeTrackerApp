package com.x0rtex.recipetrackerapp

import com.x0rtex.recipetrackerapp.data.models.IngredientInput
import com.x0rtex.recipetrackerapp.ui.components.RecipeFormState
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class RecipeFormStateTest {

    private lateinit var formState: RecipeFormState

    @Before
    fun setup() {
        formState = RecipeFormState()
    }

    @Test
    fun `validate returns false when title is blank`() {
        formState.title = ""
        formState.servings = "4"

        val result = formState.validate()

        assertFalse(result)
        assertTrue(formState.showValidationError)
    }

    @Test
    fun `validate returns false when servings is invalid`() {
        formState.title = "Test Recipe"
        formState.servings = "invalid"

        val result = formState.validate()

        assertFalse(result)
    }

    @Test
    fun `validate returns true when title and servings are valid`() {
        formState.title = "Chocolate Cake"
        formState.servings = "8"

        val result = formState.validate()

        assertTrue(result)
    }

    @Test
    fun `toRecipe creates valid recipe entity`() {
        formState.title = "Pasta"
        formState.description = "Delicious pasta"
        formState.servings = "4"
        formState.tags = "Italian, Quick"
        formState.ingredients = listOf(
            IngredientInput(name = "Pasta", amount = "200", unit = "g")
        )
        formState.instructions = listOf("Boil water", "Cook pasta")

        val recipe = formState.toRecipe()

        assertEquals("Pasta", recipe.title)
        assertEquals("Delicious pasta", recipe.description)
        assertEquals(4, recipe.servings)
        assertEquals(2, recipe.tags.size)
        assertEquals("Italian", recipe.tags[0])
        assertEquals(1, recipe.ingredients.size)
        assertEquals(2, recipe.instructions.size)
    }

    @Test
    fun `toRecipe filters out blank ingredients`() {
        formState.title = "Test"
        formState.servings = "2"
        formState.ingredients = listOf(
            IngredientInput(name = "Flour", amount = "100", unit = "g"),
            IngredientInput(name = "", amount = "", unit = "")
        )

        val recipe = formState.toRecipe()

        assertEquals(1, recipe.ingredients.size)
        assertEquals("Flour", recipe.ingredients[0].name)
    }

    @Test
    fun `addIngredient increases ingredient list size`() {
        val initialSize = formState.ingredients.size

        formState.addIngredient()

        assertEquals(initialSize + 1, formState.ingredients.size)
    }

    @Test
    fun `removeIngredient decreases ingredient list size`() {
        formState.ingredients = listOf(
            IngredientInput(name = "Item 1"),
            IngredientInput(name = "Item 2")
        )

        formState.removeIngredient(0)

        assertEquals(1, formState.ingredients.size)
        assertEquals("Item 2", formState.ingredients[0].name)
    }

    @Test
    fun `nutritional info converts to double correctly`() {
        formState.title = "Test"
        formState.servings = "1"
        formState.totalCalories = "250.5"
        formState.totalProtein = "12"

        val recipe = formState.toRecipe()

        assertEquals(250.5, recipe.totalCalories!!, 0.01)
        assertEquals(12.0, recipe.totalProtein!!, 0.01)
    }

    @Test
    fun `invalid nutritional info converts to null`() {
        formState.title = "Test"
        formState.servings = "1"
        formState.totalCalories = "invalid"

        val recipe = formState.toRecipe()

        assertNull(recipe.totalCalories)
    }
}