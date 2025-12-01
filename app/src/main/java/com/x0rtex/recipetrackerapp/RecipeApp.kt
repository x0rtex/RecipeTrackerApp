package com.x0rtex.recipetrackerapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme
import com.x0rtex.recipetrackerapp.viewmodel.FakeRecipeViewModel
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun RecipeApp(
    recipeViewModel: RecipeViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startScreen: RecipeScreen = RecipeScreen.Home
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RecipeScreen.valueOf(
        backStackEntry?.destination?.route ?: RecipeScreen.Home.name
    )

    val uiState by recipeViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen != RecipeScreen.Home,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomBar(
                currentScreen = currentScreen,
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Onboarding Screen
            composable(route = RecipeScreen.Onboarding.name) {
                OnboardingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    navController = navController
                )
            }

            // Home Screen
            composable(route = RecipeScreen.Home.name) {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    recipes = uiState.recipes,
                    onRecipeClick = { recipe ->
                        recipeViewModel.loadRecipe(recipe.id)
                        navController.navigate(RecipeScreen.ViewRecipe.name)
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
                    onCancelClick = {
                        navController.navigateUp()
                    }
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
                        navController.navigateUp()
                    },
                    onCancelClick = {
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

@Preview(showBackground = true)
@Composable
fun RecipeAppPreview() {
    val fakeViewModel = FakeRecipeViewModel()
    val navController = rememberNavController()
    val uiState by fakeViewModel.uiState.collectAsState()

    RecipeTrackerAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    currentScreen = RecipeScreen.Home,
                    canNavigateBack = false,
                    navigateUp = { }
                )
            },
            bottomBar = {
                BottomBar(
                    currentScreen = RecipeScreen.Home,
                    navController = navController
                )
            }
        ) { innerPadding ->
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                recipes = uiState.recipes,
                onRecipeClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppViewRecipePreview() {
    val fakeViewModel = FakeRecipeViewModel()
    val navController = rememberNavController()
    val uiState by fakeViewModel.uiState.collectAsState()

    RecipeTrackerAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    currentScreen = RecipeScreen.ViewRecipe,
                    canNavigateBack = true,
                    navigateUp = { }
                )
            },
            bottomBar = {
                BottomBar(
                    currentScreen = RecipeScreen.ViewRecipe,
                    navController = navController
                )
            }
        ) { innerPadding ->
            ViewRecipeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                recipe = uiState.selectedRecipe,
                onEditClick = { },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppEditRecipePreview() {
    val fakeViewModel = FakeRecipeViewModel()
    val navController = rememberNavController()
    val uiState by fakeViewModel.uiState.collectAsState()

    RecipeTrackerAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    currentScreen = RecipeScreen.EditRecipe,
                    canNavigateBack = true,
                    navigateUp = { }
                )
            },
            bottomBar = {
                BottomBar(
                    currentScreen = RecipeScreen.EditRecipe,
                    navController = navController
                )
            }
        ) { innerPadding ->
            EditRecipeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                recipe = uiState.selectedRecipe,
                onSaveClick = { },
                onCancelClick = { },
                onDeleteClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppAddRecipePreview() {
    val navController = rememberNavController()

    RecipeTrackerAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    currentScreen = RecipeScreen.AddRecipe,
                    canNavigateBack = true,
                    navigateUp = { }
                )
            },
            bottomBar = {
                BottomBar(
                    currentScreen = RecipeScreen.AddRecipe,
                    navController = navController
                )
            }
        ) { innerPadding ->
            AddRecipeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                onSaveClick = { },
                onCancelClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppSettingsPreview() {
    val navController = rememberNavController()

    RecipeTrackerAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    currentScreen = RecipeScreen.Settings,
                    canNavigateBack = false,
                    navigateUp = { }
                )
            },
            bottomBar = {
                BottomBar(
                    currentScreen = RecipeScreen.Settings,
                    navController = navController
                )
            }
        ) { innerPadding ->
            SettingsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                navController = navController
            )
        }
    }
}
