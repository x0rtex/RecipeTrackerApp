package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.x0rtex.recipetrackerapp.R

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    currentScreen: RecipeScreen,
    navController: NavHostController = rememberNavController()
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = {
            if (currentScreen != RecipeScreen.AddRecipe && currentScreen != RecipeScreen.EditRecipe) {
                // Home Screen Button
                NavButton(
                    modifier = modifier,
                    navController = navController,
                    navigateTo = RecipeScreen.Home,
                    icon = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.app_name)
                )

                // Add Recipe Button
                NavButton(
                    modifier = modifier,
                    navController = navController,
                    navigateTo = RecipeScreen.AddRecipe,
                    icon = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.recipe_add)
                )

                // Settings Button
                NavButton(
                    modifier = modifier,
                    navController = navController,
                    navigateTo = RecipeScreen.Settings,
                    icon = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            } else {
                // Confirm Button
                Button(
                    modifier = modifier,
                    onClick = {
                        navController.navigate(RecipeScreen.Home.name)
                    },
                    content = {
                        Text(
                            modifier = modifier,
                            text = stringResource(R.string.recipe_submit)
                        )
                    }
                )

                // Cancel Button
                Button(
                    modifier = modifier,
                    onClick = {
                        navController.navigate(RecipeScreen.Home.name)
                    },
                    content = {
                        Text(
                            modifier = modifier,
                            text = stringResource(R.string.recipe_cancel)
                        )
                    }
                )
            }
        },
    )
}

@Composable
fun NavButton(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigateTo: RecipeScreen,
    icon: ImageVector,
    contentDescription: String
) {
    IconButton(
        modifier = modifier,
        onClick = {
            navController.navigate(navigateTo.name) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
fun BottomBarPreview(
    modifier: Modifier = Modifier,
) {
    BottomBar(
        modifier = modifier,
        currentScreen = RecipeScreen.Home,
    )
}
