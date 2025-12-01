package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentScreen != RecipeScreen.AddRecipe && currentScreen != RecipeScreen.EditRecipe) {

                    // Home Screen Button
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
                        contentDescription = stringResource(R.string.recipe_add)
                    )

                    // Settings Button
                    NavButton(
                        navController = navController,
                        navigateTo = RecipeScreen.Settings,
                        icon = Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.settings)
                    )

                } else {

                    // Confirm Button
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .padding(8.dp),
                        onClick = {
                            navController.navigate(RecipeScreen.Home.name)
                        },
                        content = {
                            Text(
                                fontSize = 20.sp,
                                text = stringResource(R.string.recipe_submit)
                            )
                        }
                    )

                    // Cancel Button
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        onClick = {
                            navController.navigate(RecipeScreen.Home.name)
                        },
                        content = {
                            Text(
                                fontSize = 20.sp,
                                text = stringResource(R.string.recipe_cancel)
                            )
                        }
                    )

                }
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
    BottomBar(
        currentScreen = RecipeScreen.Home,
    )
}

@Preview(showBackground = true)
@Composable
fun BottomBarOptionsPreview() {
    BottomBar(
        currentScreen = RecipeScreen.AddRecipe,
    )
}
