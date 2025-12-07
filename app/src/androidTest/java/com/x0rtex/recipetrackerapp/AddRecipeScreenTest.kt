package com.x0rtex.recipetrackerapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.x0rtex.recipetrackerapp.ui.screens.AddRecipeScreen
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Basic UI test for AddRecipeScreen
@RunWith(AndroidJUnit4::class)
class AddRecipeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addRecipeScreen_displaysFormElements() {
        composeTestRule.setContent {
            RecipeTrackerAppTheme {
                AddRecipeScreen(onSaveClick = {})
            }
        }

        // Verify form elements are displayed
        composeTestRule.onNodeWithText("Recipe Title *").assertExists()
        composeTestRule.onNodeWithText("Description").assertExists()
        composeTestRule.onNodeWithText("Servings *").assertExists()
        composeTestRule.onNodeWithText("Ingredients").assertExists()
        composeTestRule.onNodeWithText("Instructions").assertExists()
    }

    @Test
    fun addRecipeScreen_canEnterTitle() {
        composeTestRule.setContent {
            RecipeTrackerAppTheme {
                AddRecipeScreen(onSaveClick = {})
            }
        }

        // Find the title field
        composeTestRule
            .onAllNodesWithText("Recipe Title *")
            .onFirst()
            .performTextInput("Chocolate Cake")

        // Verify text was entered
        composeTestRule
            .onNodeWithText("Chocolate Cake")
            .assertExists()
    }

    @Test
    fun addRecipeScreen_saveButtonExists() {
        composeTestRule.setContent {
            RecipeTrackerAppTheme {
                AddRecipeScreen(onSaveClick = {})
            }
        }

        // Verify save button is there
        composeTestRule
            .onNodeWithText("Save Recipe")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun addRecipeScreen_addIngredientButtonWorks() {
        composeTestRule.setContent {
            RecipeTrackerAppTheme {
                AddRecipeScreen(onSaveClick = {})
            }
        }

        // Click the Add Ingredient button
        composeTestRule
            .onNodeWithText("Add Ingredient")
            .performClick()

        // Verify button still exists
        composeTestRule
            .onNodeWithText("Add Ingredient")
            .assertExists()
    }
}
