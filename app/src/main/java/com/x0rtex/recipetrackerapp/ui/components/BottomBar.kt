package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.x0rtex.recipetrackerapp.R

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home Button
                NavButton(
                    navController = navController,
                    navigateTo = RecipeScreen.Home,
                    icon = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.app_name)
                )

                // Add Recipe Button
                NavButton(
                    navController = navController,
                    navigateTo = RecipeScreen.AddRecipe,
                    icon = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_recipe)
                )

                // Settings Button
                NavButton(
                    navController = navController,
                    navigateTo = RecipeScreen.Settings,
                    icon = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
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
        modifier = modifier.size(56.dp),
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
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarNavPreview() {
    BottomBar()
}