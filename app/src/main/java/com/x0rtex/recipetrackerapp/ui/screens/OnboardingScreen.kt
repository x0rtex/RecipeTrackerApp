package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.x0rtex.recipetrackerapp.viewmodel.RecipeViewModel

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = null,
        )


    }
}