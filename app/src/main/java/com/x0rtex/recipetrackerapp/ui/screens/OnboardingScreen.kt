package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.ui.components.RecipeScreen

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = modifier
                .height(10.dp),
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = null,
        )
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.onboarding_welcome)
        )
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.onboarding_text)
        )
        Button(
            modifier = modifier,
            onClick = { navController.navigate(RecipeScreen.Home.name) }
        ) {
            Text(
                modifier = modifier,
                text = stringResource(id = R.string.onboarding_get_started)
            )
        }
    }
}