package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.x0rtex.recipetrackerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    currentScreen: RecipeScreen,
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = {
            // Home button
            IconButton(
                modifier = modifier,
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.app_name)
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
