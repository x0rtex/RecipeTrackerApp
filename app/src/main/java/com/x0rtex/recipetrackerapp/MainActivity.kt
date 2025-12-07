package com.x0rtex.recipetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.x0rtex.recipetrackerapp.data.models.UserSettings
import com.x0rtex.recipetrackerapp.data.repository.SettingsManager
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme
import com.x0rtex.recipetrackerapp.worker.WorkManagerSetup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialise Work Manager
        WorkManagerSetup.schedulePeriodicTasks(applicationContext)

        setContent {
            // Get settings to check dark mode
            val context = LocalContext.current
            val settingsManager = remember { SettingsManager(context) }
            val settings by settingsManager.settingsFlow.collectAsState(initial = UserSettings())

            // Set dark mode from settings
            RecipeTrackerAppTheme(darkTheme = settings.isDarkTheme) {
                RecipeApp()
            }
        }
    }
}
