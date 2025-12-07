package com.x0rtex.recipetrackerapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.x0rtex.recipetrackerapp.data.models.UserSettings
import com.x0rtex.recipetrackerapp.data.repository.SettingsManager
import com.x0rtex.recipetrackerapp.ui.components.BottomBar
import com.x0rtex.recipetrackerapp.ui.components.TopBar
import com.x0rtex.recipetrackerapp.ui.components.RecipeScreen
import com.x0rtex.recipetrackerapp.ui.screens.AddRecipeScreen
import com.x0rtex.recipetrackerapp.ui.screens.EditRecipeScreen
import com.x0rtex.recipetrackerapp.ui.screens.HomeScreen
import com.x0rtex.recipetrackerapp.ui.screens.OnboardingScreen
import com.x0rtex.recipetrackerapp.ui.screens.SettingsScreen
import com.x0rtex.recipetrackerapp.ui.screens.ViewRecipeScreen
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun RecipeApp(
    recipeViewModel: RecipeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }
    val settings by settingsManager.settingsFlow.collectAsState(initial = UserSettings())

    // Onboarding Screen If First Time
    val startDestination = if (settings.isFirstLaunch) {
        RecipeScreen.Onboarding.name
    } else {
        RecipeScreen.Home.name
    }

    // Track current screen and back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RecipeScreen.valueOf(
        value = backStackEntry?.destination?.route ?: RecipeScreen.Home.name
    )

    val uiState by recipeViewModel.uiState.collectAsState()

    Scaffold(
        // Top Bar
        topBar = {
            TopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen != RecipeScreen.Home,
                navigateUp = { navController.navigateUp() }
            )
        },

        // Bottom Bar
        bottomBar = {
            BottomBar(
                navController = navController
            )
        }
    ) { innerPadding ->

        // Nav Host
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues = innerPadding)
        ) {

            // Onboarding Screen
            composable(route = RecipeScreen.Onboarding.name) {
                OnboardingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = dimensionResource(id = R.dimen.padding_medium)),
                    navController = navController
                )
            }

            // Home Screen
            composable(route = RecipeScreen.Home.name) {
                val searchQuery by recipeViewModel.searchQuery.collectAsState()
                val selectedTag by recipeViewModel.selectedTag.collectAsState()
                val availableTags by recipeViewModel.availableTags.collectAsState()
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = dimensionResource(id = R.dimen.padding_medium)),
                    recipes = uiState.recipes,
                    searchQuery = searchQuery,
                    selectedTag = selectedTag,
                    availableTags = availableTags,
                    onSearchQueryChange = { recipeViewModel.updateSearchQuery(query = it) },
                    onTagSelect = { recipeViewModel.selectTag(tag = it) },
                    onRecipeClick = { recipe ->
                        recipeViewModel.loadRecipe(recipe.id)
                        navController.navigate(route = RecipeScreen.ViewRecipe.name)
                    }
                )
            }

            // Settings Screen
            composable(route = RecipeScreen.Settings.name) {
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    navController = navController
                )
            }

            // View Recipe Screen
            composable(route = RecipeScreen.ViewRecipe.name) {
                ViewRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    recipe = uiState.selectedRecipe,
                    onEditClick = {
                        navController.navigate(RecipeScreen.EditRecipe.name)
                    },
                )
            }

            // Add Recipe Screen
            composable(route = RecipeScreen.AddRecipe.name) {
                AddRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    onSaveClick = { recipe ->
                        recipeViewModel.addRecipe(recipe)
                        navController.navigateUp()
                    },
                )
            }

            // Edit Recipe Screen
            composable(route = RecipeScreen.EditRecipe.name) {
                EditRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    recipe = uiState.selectedRecipe,
                    onSaveClick = { recipe ->
                        recipeViewModel.updateRecipe(recipe)
                        recipeViewModel.loadRecipe(recipe.id)
                        navController.navigateUp()
                    },
                    onDeleteClick = { recipe ->
                        recipeViewModel.deleteRecipe(recipe)
                        navController.popBackStack(RecipeScreen.Home.name, inclusive = false)
                    }
                )
            }
        }
    }
}
