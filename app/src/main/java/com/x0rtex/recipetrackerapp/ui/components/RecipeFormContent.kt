package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.data.models.IngredientInput
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import kotlin.collections.ifEmpty
import kotlin.collections.plus

// Form State
class RecipeFormState(recipe: RecipeEntity? = null) {
    var title by mutableStateOf(recipe?.title ?: "")
    var description by mutableStateOf(recipe?.description ?: "")
    var tags by mutableStateOf(
        recipe?.tags?.joinToString(", ") ?: ""
    )
    var servings by mutableStateOf(recipe?.servings?.toString() ?: "4")
    var notes by mutableStateOf(recipe?.notes ?: "")
    var imageUri by mutableStateOf(recipe?.image)
    var ingredients by mutableStateOf(
        recipe?.ingredients?.map {
            IngredientInput(
                name = it.name,
                amount = it.amount?.toString() ?: "",
                unit = it.unit ?: ""
            )
        }?.ifEmpty { listOf(IngredientInput()) }
            ?: listOf(IngredientInput())
    )
    var instructions by mutableStateOf(
        recipe?.instructions?.ifEmpty { listOf("") } ?: listOf("")
    )
    var showValidationError by mutableStateOf(false)
    var totalCalories by mutableStateOf(recipe?.totalCalories?.toString() ?: "")
    var totalFat by mutableStateOf(recipe?.totalFat?.toString() ?: "")
    var totalSugar by mutableStateOf(recipe?.totalSugar?.toString() ?: "")
    var totalProtein by mutableStateOf(recipe?.totalProtein?.toString() ?: "")
    var showNutritionalInfo by mutableStateOf(
        recipe?.totalCalories != null ||
                recipe?.totalFat != null ||
                recipe?.totalSugar != null ||
                recipe?.totalProtein != null
    )

    // Validates form inputs
    fun validate(): Boolean {
        val isValid = title.isNotBlank() && servings.toIntOrNull() != null
        if (!isValid) showValidationError = true
        return isValid
    }

    // Converts form state to new RecipeEntity
    fun toRecipe(): RecipeEntity {
        return RecipeEntity(
            title = title.trim(),
            description = description.trim(),
            tags = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            servings = servings.toInt(),
            ingredients = ingredients.filter { it.name.isNotBlank() }
                .map { it.toIngredientEntity() },
            instructions = instructions.filter { it.isNotBlank() },
            notes = notes.trim().takeIf { it.isNotEmpty() },
            image = imageUri,
            totalCalories = totalCalories.toDoubleOrNull(),
            totalFat = totalFat.toDoubleOrNull(),
            totalSugar = totalSugar.toDoubleOrNull(),
            totalProtein = totalProtein.toDoubleOrNull()
        )
    }

    // Converts form state to updated RecipeEntity
    fun toUpdatedRecipe(originalRecipe: RecipeEntity): RecipeEntity {
        return originalRecipe.copy(
            title = title.trim(),
            description = description.trim(),
            tags = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            servings = servings.toInt(),
            ingredients = ingredients.filter { it.name.isNotBlank() }
                .map { it.toIngredientEntity() },
            instructions = instructions.filter { it.isNotBlank() },
            notes = notes.trim().takeIf { it.isNotEmpty() },
            image = imageUri,
            totalCalories = totalCalories.toDoubleOrNull(),
            totalFat = totalFat.toDoubleOrNull(),
            totalSugar = totalSugar.toDoubleOrNull(),
            totalProtein = totalProtein.toDoubleOrNull()
        )
    }

    // Ingredient operations
    fun updateIngredient(index: Int, ingredient: IngredientInput) {
        ingredients = ingredients.toMutableList().apply { this[index] = ingredient }
    }

    fun removeIngredient(index: Int) {
        ingredients = ingredients.toMutableList().apply { removeAt(index) }
    }

    fun addIngredient() {
        ingredients = ingredients + IngredientInput()
    }

    // Instruction operations
    fun updateInstruction(index: Int, instruction: String) {
        instructions = instructions.toMutableList().apply { this[index] = instruction }
    }

    fun removeInstruction(index: Int) {
        instructions = instructions.toMutableList().apply { removeAt(index) }
    }

    fun addInstruction() {
        instructions = instructions + ""
    }
}

// Remember form state for adding recipe
@Composable
fun rememberRecipeFormState(): RecipeFormState {
    return remember { RecipeFormState() }
}

// Remember form state for editing recipe
@Composable
fun rememberRecipeFormState(recipe: RecipeEntity): RecipeFormState {
    return remember(recipe.id) { RecipeFormState(recipe) }
}

// Complete recipe form content
@Composable
fun RecipeFormContent(
    modifier: Modifier = Modifier,
    formState: RecipeFormState,
    onAddPhotoClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        BasicInfoFields(formState)
        IngredientsSection(formState)
        InstructionsSection(formState)
        NutritionalInfoSection(formState)
        NotesField(formState)
        RecipePhotoSection(
            imageUri = formState.imageUri,
            onAddPhotoClick = onAddPhotoClick,
            onRemovePhoto = { formState.imageUri = null }
        )

        Spacer(modifier = Modifier.height(height = 80.dp))
    }
}

// Basic info fields
@Composable
fun BasicInfoFields(formState: RecipeFormState) {
    // Title field
    OutlinedTextField(
        value = formState.title,
        onValueChange = { formState.title = it },
        label = { Text(text = stringResource(id = R.string.recipe_title_required)) },
        isError = formState.showValidationError && formState.title.isBlank(),
        supportingText = {
            if (formState.showValidationError && formState.title.isBlank()) {
                Text(text = stringResource(id = R.string.title_is_required))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )

    // Description field
    OutlinedTextField(
        value = formState.description,
        onValueChange = { formState.description = it },
        label = { Text(text = stringResource(id = R.string.description)) },
        minLines = 3,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )

    // Tags field
    OutlinedTextField(
        value = formState.tags,
        onValueChange = { formState.tags = it },
        label = { Text(text = stringResource(id = R.string.tags_comma_separated)) },
        placeholder = { Text(text = stringResource(id = R.string.e_g_dessert_baking_quick)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )

    // Servings field
    OutlinedTextField(
        value = formState.servings,
        onValueChange = { formState.servings = it.filter { char -> char.isDigit() } },
        label = { Text(text = stringResource(id = R.string.servings_required)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = formState.showValidationError && formState.servings.toIntOrNull() == null,
        supportingText = {
            if (formState.showValidationError && formState.servings.toIntOrNull() == null) {
                Text(text = stringResource(id = R.string.valid_servings_number_required))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}

// Ingredients section with add/remove
@Composable
fun IngredientsSection(formState: RecipeFormState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.ingredients),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Show all ingredients
            formState.ingredients.forEachIndexed { index, ingredient ->
                IngredientInputRow(
                    ingredient = ingredient,
                    onIngredientChange = { formState.updateIngredient(index, ingredient = it) },
                    onDelete = { formState.removeIngredient(index) },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Add new ingredient button
            OutlinedButton(
                onClick = { formState.addIngredient() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.width(width = 8.dp))
                Text(text = stringResource(id = R.string.add_ingredient))
            }
        }
    }
}

// Single ingredient input row
@Composable
fun IngredientInputRow(
    ingredient: IngredientInput,
    onIngredientChange: (IngredientInput) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ingredient Amount
        OutlinedTextField(
            value = ingredient.amount,
            onValueChange = { onIngredientChange(ingredient.copy(amount = it)) },
            placeholder = { Text(text = stringResource(id = R.string.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(weight = 0.25f),
            singleLine = true
        )

        // Ingredient Unit
        OutlinedTextField(
            value = ingredient.unit,
            onValueChange = { onIngredientChange(ingredient.copy(unit = it)) },
            placeholder = { Text(text = stringResource(id = R.string.unit)) },
            modifier = Modifier.weight(weight = 0.25f),
            singleLine = true
        )

        // Ingredient Name
        OutlinedTextField(
            value = ingredient.name,
            onValueChange = { onIngredientChange(ingredient.copy(name = it)) },
            placeholder = { Text(text = stringResource(id = R.string.ingredient)) },
            modifier = Modifier.weight(weight = 0.5f),
            singleLine = true
        )

        // Remove Ingredient Button
        IconButton(
            onClick = onDelete,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.remove_ingredient)
            )
        }
    }
}

// Instructions section with numbered steps
@Composable
fun InstructionsSection(formState: RecipeFormState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.instructions),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Show all instructions
            formState.instructions.forEachIndexed { index, instruction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    Text(text = "${index + 1}.", modifier = Modifier.padding(top = 16.dp))

                    OutlinedTextField(
                        value = instruction,
                        onValueChange = { formState.updateInstruction(index, instruction = it) },
                        placeholder = {
                            Text(
                                text = stringResource(
                                    id = R.string.instruction_step,
                                    index + 1
                                )
                            )
                        },
                        modifier = Modifier.weight(weight = 1f)
                    )

                    IconButton(
                        onClick = { formState.removeInstruction(index) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.remove_step)
                        )
                    }
                }
            }

            // Add new instruction button
            OutlinedButton(
                onClick = { formState.addInstruction() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.width(width = 8.dp))
                Text(text = stringResource(id = R.string.add_instruction_step))
            }
        }
    }
}

// Nutritional information section (collapsible)
@Composable
fun NutritionalInfoSection(formState: RecipeFormState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            // Header with toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.nutritional_values),
                    style = MaterialTheme.typography.titleMedium
                )

                TextButton(
                    onClick = { formState.showNutritionalInfo = !formState.showNutritionalInfo }
                ) {
                    Text(
                        text = if (formState.showNutritionalInfo)
                            stringResource(id = R.string.hide)
                        else
                            stringResource(id = R.string.add)
                    )
                }
            }

            // Show fields when toggled
            if (formState.showNutritionalInfo) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.nutritional_values),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Calories
                OutlinedTextField(
                    value = formState.totalCalories,
                    onValueChange = { formState.totalCalories = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text(text = stringResource(id = R.string.calories)) },
                    placeholder = { Text("250") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    suffix = { Text("kcal") }
                )

                // Fat
                OutlinedTextField(
                    value = formState.totalFat,
                    onValueChange = { formState.totalFat = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text(text = stringResource(id = R.string.fat)) },
                    placeholder = { Text("15") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    suffix = { Text("g") }
                )

                // Sugar
                OutlinedTextField(
                    value = formState.totalSugar,
                    onValueChange = { formState.totalSugar = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text(text = stringResource(id = R.string.sugar)) },
                    placeholder = { Text("8") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    suffix = { Text("g") }
                )

                // Protein
                OutlinedTextField(
                    value = formState.totalProtein,
                    onValueChange = { formState.totalProtein = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text(text = stringResource(id = R.string.protein)) },
                    placeholder = { Text("12") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    suffix = { Text("g") }
                )
            }
        }
    }
}

// Notes field
@Composable
fun NotesField(formState: RecipeFormState) {
    OutlinedTextField(
        value = formState.notes,
        onValueChange = { formState.notes = it },
        label = { Text(text = stringResource(id = R.string.notes_optional)) },
        minLines = 2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}

// Recipe photo section with add/remove
@Composable
fun RecipePhotoSection(
    imageUri: String?,
    onAddPhotoClick: () -> Unit,
    onRemovePhoto: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.recipe_photo),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (imageUri != null) {
                // Show selected image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 200.dp)
                ) {
                    AsyncImage(
                        model = imageUri.toUri(),
                        contentDescription = stringResource(id = R.string.recipe_photo),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(size = 8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Remove button overlay
                    IconButton(
                        onClick = onRemovePhoto,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(all = 8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(percent = 50),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.remove_photo),
                                modifier = Modifier.padding(all = 8.dp)
                            )
                        }
                    }
                }
            } else {
                // Show add photo button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 150.dp)
                        .clip(shape = RoundedCornerShape(size = 8.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .clickable(onClick = onAddPhotoClick),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_photo),
                            modifier = Modifier.size(size = 48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(height = 8.dp))
                        Text(
                            text = stringResource(id = R.string.add_recipe_photo),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
