package com.x0rtex.recipetrackerapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.x0rtex.recipetrackerapp.ui.components.BottomBar
import com.x0rtex.recipetrackerapp.ui.components.TopBar
import com.x0rtex.recipetrackerapp.ui.components.RecipeScreen
import com.x0rtex.recipetrackerapp.ui.screens.AddRecipeScreen
import com.x0rtex.recipetrackerapp.ui.screens.EditRecipeScreen
import com.x0rtex.recipetrackerapp.ui.screens.HomeScreen
import com.x0rtex.recipetrackerapp.ui.screens.OnboardingScreen
import com.x0rtex.recipetrackerapp.ui.screens.SettingsScreen
import com.x0rtex.recipetrackerapp.ui.screens.ViewRecipeScreen
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun RecipeApp(
    viewModel: RecipeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RecipeScreen.valueOf(
        backStackEntry?.destination?.route ?: RecipeScreen.Home.name
    )

    Scaffold(
        topBar = {
            TopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomBar(
                currentScreen = currentScreen,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RecipeScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Onboarding Screen
            composable(route = RecipeScreen.Onboarding.name) {
                OnboardingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // Home Screen
            composable(route = RecipeScreen.Home.name) {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // Settings Screen
            composable(route = RecipeScreen.Settings.name) {
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // View Recipe Screen
            composable(route = RecipeScreen.ViewRecipe.name) {
                ViewRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // Add Recipe Screen
            composable(route = RecipeScreen.AddRecipe.name) {
                AddRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // Edit Recipe Screen
            composable(route = RecipeScreen.EditRecipe.name) {
                EditRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}

@Preview
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}
