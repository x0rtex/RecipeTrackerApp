package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme

@Composable
fun ViewRecipeScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity?,
    onEditClick: () -> Unit
) {
    var servingMultiplier by remember { mutableDoubleStateOf(value = 1.0) }

    if (recipe == null) {
        RecipeNotFoundContent(modifier)
        return
    }

    Scaffold(

        // Floating Edit Button
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_recipe)
                )
            }
        },
        contentWindowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0)
    ) { paddingValues ->

        // Recipe Content
        RecipeContent(
            modifier = modifier.padding(paddingValues),
            recipe = recipe,
            servingMultiplier = servingMultiplier,
            onServingMultiplierChange = { servingMultiplier = it },
        )
    }
}

// Recipe Not Found Text
@Composable
private fun RecipeNotFoundContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.recipe_not_found),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// All Recipe Content
@Composable
private fun RecipeContent(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity,
    servingMultiplier: Double,
    onServingMultiplierChange: (Double) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        // Recipe Header
        RecipeHeader(
            title = recipe.title,
            description = recipe.description,
            tags = recipe.tags
        )

        // Recipe Image
        if (recipe.image != null) {
            RecipeImageHeader(imageUri = recipe.image)
        }

        ServingsAdjuster(
            baseServings = recipe.servings,
            servingMultiplier = servingMultiplier,
            onServingMultiplierChange = onServingMultiplierChange
        )

        // Ingredients Card
        IngredientsCard(
            ingredients = recipe.ingredients,
            servingMultiplier = servingMultiplier
        )

        // Instructions Card
        if (recipe.instructions.isNotEmpty()) {
            InstructionsCard(instructions = recipe.instructions)
        }

        // Nutritional Info Card
        val hasNutritionalData = recipe.totalCalories != null ||
                recipe.totalFat != null ||
                recipe.totalSugar != null ||
                recipe.totalProtein != null

        if (hasNutritionalData) {
            NutritionalInfoCard(
                recipe = recipe,
                servingMultiplier = servingMultiplier
            )
        }

        // Notes Card
        if (!recipe.notes.isNullOrBlank()) {
            NotesCard(notes = recipe.notes)
        }

        Spacer(modifier = Modifier.height(height = 80.dp))
    }
}

// Recipe Image
@Composable
private fun RecipeImageHeader(
    imageUri: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 250.dp)
            .padding(bottom = 16.dp),
    ) {
        AsyncImage(
            model = imageUri.toUri(),
            contentDescription = stringResource(id = R.string.recipe_photo),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

// Recipe Header
@Composable
private fun RecipeHeader(
    title: String,
    description: String,
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (description.isNotEmpty()) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (tags.isNotEmpty()) {
            RecipeTags(tags = tags)
        }
    }
}

// Recipe Tags
@Composable
private fun RecipeTags(
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        tags.forEach { tag ->
            AssistChip(
                onClick = { },
                label = { Text(text = tag) }
            )
        }
    }
}

// Servings Adjuster
@Composable
private fun ServingsAdjuster(
    baseServings: Int,
    servingMultiplier: Double,
    onServingMultiplierChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {

    // Calculate new servings based on multiplier
    val currentServings = (baseServings * servingMultiplier)
        .toInt()
        .coerceAtLeast(minimumValue = 1)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.servings),
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Decrease Button
                TextButton(
                    onClick = {
                        val newServings = currentServings - 1
                        if (newServings >= 1) {
                            onServingMultiplierChange(newServings.toDouble() / baseServings)
                        }
                    },
                    enabled = currentServings > 1
                ) {
                    Text(text = "-")
                }
                Text(
                    text = "$currentServings serving${if (currentServings != 1) "s" else ""}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Increase Button
                TextButton(
                    onClick = {
                        val newServings = currentServings + 1
                        onServingMultiplierChange(newServings.toDouble() / baseServings)
                    }
                ) {
                    Text(text = "+")
                }
            }
        }
    }
}

// Ingredients Card
@Composable
private fun IngredientsCard(
    ingredients: List<IngredientEntity>,
    servingMultiplier: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.ingredients),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ingredients.forEach { ingredient ->
                IngredientItem(
                    ingredient = ingredient,
                    servingMultiplier = servingMultiplier
                )
            }
        }
    }
}

// Single Ingredient Item
@Composable
private fun IngredientItem(
    ingredient: IngredientEntity,
    servingMultiplier: Double
) {
    // Format and display ingredient
    val displayText = formatIngredient(ingredient, servingMultiplier)
    Text(
        text = displayText,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

// Format ingredient text by scaling amount
private fun formatIngredient(ingredient: IngredientEntity, multiplier: Double): String {
    val scaledAmount = ingredient.amount?.times(other = multiplier)

    return when {
        // Has amount and unit
        scaledAmount != null && !ingredient.unit.isNullOrBlank() -> {
            "${scaledAmount.format()} ${ingredient.unit} ${ingredient.name}".trim()
        }
        // Has amount but no unit
        scaledAmount != null -> {
            "${scaledAmount.format()} ${ingredient.name}".trim()
        }
        // Has unit but no amount
        !ingredient.unit.isNullOrBlank() -> {
            "${ingredient.unit} ${ingredient.name}".trim()
        }
        // Only name
        else -> ingredient.name
    }
}

// Instructions Card
@Composable
private fun InstructionsCard(
    instructions: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.instructions),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            instructions.forEachIndexed { index, instruction ->
                InstructionStep(number = index + 1, text = instruction)
            }
        }
    }
}

// Single Instruction Step
@Composable
private fun InstructionStep(
    number: Int,
    text: String
) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$number. ",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Notes Card
@Composable
private fun NotesCard(
    notes: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.notes),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = notes,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Nutritional Info Card
@Composable
private fun NutritionalInfoCard(
    recipe: RecipeEntity,
    servingMultiplier: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.nutritional_values),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Show nutritional values scaled by servings
            recipe.totalCalories?.let { calories ->
                NutritionalRow(
                    label = stringResource(id = R.string.calories),
                    value = (calories * servingMultiplier).format(),
                    unit = "kcal"
                )
            }

            recipe.totalFat?.let { fat ->
                NutritionalRow(
                    label = stringResource(id = R.string.fat),
                    value = (fat * servingMultiplier).format(),
                    unit = "g"
                )
            }

            recipe.totalSugar?.let { sugar ->
                NutritionalRow(
                    label = stringResource(id = R.string.sugar),
                    value = (sugar * servingMultiplier).format(),
                    unit = "g"
                )
            }

            recipe.totalProtein?.let { protein ->
                NutritionalRow(
                    label = stringResource(id = R.string.protein),
                    value = (protein * servingMultiplier).format(),
                    unit = "g"
                )
            }
        }
    }
}

@Composable
private fun NutritionalRow(
    label: String,
    value: String,
    unit: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = "$value $unit",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// Format double to 1 decimal or integer
private fun Double.format(): String =
    if (this % 1.0 == 0.0) this.toInt().toString()
    else String.format("%.1f", this)

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ViewRecipeScreenPreview() {
    RecipeTrackerAppTheme {
        ViewRecipeScreen(
            recipe = RecipeEntity(
                id = 1,
                title = "Chocolate Cake",
                description = "A delicious chocolate cake recipe",
                tags = listOf("Dessert", "Baking"),
                ingredients = listOf(
                    IngredientEntity(name = "Flour", amount = 2.0, unit = "cups"),
                    IngredientEntity(name = "Sugar", amount = 1.5, unit = "cups"),
                    IngredientEntity(name = "Cocoa", amount = 0.75, unit = "cups")
                ),
                instructions = listOf(
                    "Preheat oven to 180°C",
                    "Mix dry ingredients",
                    "Add wet ingredients",
                    "Bake for 30 minutes"
                ),
                servings = 8,
                notes = "Best served warm",
                totalCalories = 450.0,
                totalFat = 20.0,
                totalSugar = 35.0,
                totalProtein = 6.0
            ),
            onEditClick = {}
        )
    }
}