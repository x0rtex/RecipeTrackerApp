package com.x0rtex.recipetrackerapp.data.models

data class UserSettings(
    val isDarkTheme: Boolean = false,
    val isFirstLaunch: Boolean = true,
    val defaultServings: Int = 2
)

object SettingsKeys {
    const val IS_DARK_THEME = "is_dark_theme"
    const val IS_FIRST_LAUNCH = "is_first_launch"
    const val DEFAULT_SERVINGS = "default_servings"
}
