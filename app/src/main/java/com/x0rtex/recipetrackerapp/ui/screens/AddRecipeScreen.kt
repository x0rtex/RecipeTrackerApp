package com.x0rtex.recipetrackerapp.ui.screens

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.data.models.IngredientEntity
import com.x0rtex.recipetrackerapp.data.models.IngredientInput
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.ui.components.ImagePicker

@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    onSaveClick: (RecipeEntity) -> Unit
) {
    val formState = rememberRecipeFormState()

    var showImagePicker by remember { mutableStateOf(value = false) }

    Scaffold(
        // Show floating save button
        floatingActionButton = {
            SaveRecipeFab(
                onClick = {
                    if (formState.validate()) {
                        onSaveClick(formState.toRecipe())
                    }
                }
            )
        }
    ) { paddingValues ->
        // Show recipe form
        RecipeFormContent(
            modifier = modifier.padding(paddingValues),
            formState = formState,
            onAddPhotoClick = { showImagePicker = true }
        )
    }

    // Show Image Picker When Triggered
    if (showImagePicker) {
        ImagePicker(
            onImageSelected = { uri ->
                formState.imageUri = uri.toString()
                showImagePicker = false
            },
            onDismiss = { showImagePicker = false }
        )
    }
}

// Save Recipe Floating Action Button
@Composable
private fun SaveRecipeFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = null)
        Spacer(Modifier.width(width = 8.dp))
        Text(text = stringResource(id = R.string.save_recipe))
    }
}

// All recipe form content
@Composable
private fun RecipeFormContent(
    modifier: Modifier = Modifier,
    onAddPhotoClick: () -> Unit,
    formState: RecipeFormState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        // Show basic info
        BasicInfoFields(formState)
        IngredientsSection(formState)
        InstructionsSection(formState)
        NotesField(formState)

        // Show Recipe Photo
        RecipePhotoSection(
            imageUri = formState.imageUri,
            onAddPhotoClick = onAddPhotoClick,
            onRemovePhoto = { formState.imageUri = null }
        )

        Spacer(modifier = Modifier.height(height = 80.dp))
    }
}

@Composable
private fun RecipePhotoSection(
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

@Composable
private fun BasicInfoFields(formState: RecipeFormState) {
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

// Ingredients Form
@Composable
private fun IngredientsSection(formState: RecipeFormState) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)) {
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

            // Add new ingredient
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

// Instructions Form
@Composable
private fun InstructionsSection(formState: RecipeFormState) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)) {
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
                            Text(text = stringResource(
                                id = R.string.instruction_step,
                                index + 1
                            ))
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

            // Add new instruction
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

// Notes Field
@Composable
private fun NotesField(formState: RecipeFormState) {
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

// Ingredient Input Row
@Composable
private fun IngredientInputRow(
    ingredient: IngredientInput,
    onIngredientChange: (IngredientInput) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        // Ingredient Amount
        OutlinedTextField(
            value = ingredient.amount,
            onValueChange = { onIngredientChange(ingredient.copy(amount = it)) },
            placeholder = { Text(text = stringResource(id = R.string.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(weight = 0.3f)
        )

        // Ingredient Unit
        OutlinedTextField(
            value = ingredient.unit,
            onValueChange = { onIngredientChange(ingredient.copy(unit = it)) },
            placeholder = { Text(text = stringResource(id = R.string.unit)) },
            modifier = Modifier.weight(weight = 0.25f)
        )

        // Ingredient Name
        OutlinedTextField(
            value = ingredient.name,
            onValueChange = { onIngredientChange(ingredient.copy(name = it)) },
            placeholder = { Text(text = stringResource(id = R.string.ingredient)) },
            modifier = Modifier.weight(weight = 0.45f)
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

// Remember Recipe Form State
@Composable
private fun rememberRecipeFormState(): RecipeFormState {
    return remember { RecipeFormState() }
}

// Recipe Form State
private class RecipeFormState {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var tags by mutableStateOf("")
    var servings by mutableStateOf("4")
    var notes by mutableStateOf("")
    var imageUri by mutableStateOf<String?>(null)
    var ingredients by mutableStateOf(listOf<IngredientInput>())
    var instructions by mutableStateOf(listOf(""))
    var showValidationError by mutableStateOf(false)

    // Validate form
    fun validate(): Boolean {
        val isValid = title.isNotBlank() && servings.toIntOrNull() != null
        if (!isValid) showValidationError = true
        return isValid
    }

    // Convert form to recipe
    fun toRecipe(): RecipeEntity {
        return RecipeEntity(
            title = title.trim(),
            description = description.trim(),
            tags = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            servings = servings.toInt(),
            ingredients = ingredients.filter { it.name.isNotBlank() }.map { it.toIngredientEntity() },
            instructions = instructions.filter { it.isNotBlank() },
            notes = notes.trim().takeIf { it.isNotEmpty() },
            image = imageUri
        )
    }

    // Update Ingredient
    fun updateIngredient(index: Int, ingredient: IngredientInput) {
        ingredients = ingredients.toMutableList().apply { this[index] = ingredient }
    }

    // Remove Ingredient
    fun removeIngredient(index: Int) {
        ingredients = ingredients.toMutableList().apply { removeAt(index) }
    }

    // Add Ingredient
    fun addIngredient() {
        ingredients = ingredients + IngredientInput()
    }

    // Update Instruction
    fun updateInstruction(index: Int, instruction: String) {
        instructions = instructions.toMutableList().apply { this[index] = instruction }
    }

    // Remove Instruction
    fun removeInstruction(index: Int) {
        instructions = instructions.toMutableList().apply { removeAt(index) }
    }

    // Add Instruction
    fun addInstruction() {
        instructions = instructions + ""
    }
}

// Ingredient Entity
data class IngredientInput(
    val name: String = "",
    val amount: String = "",
    val unit: String = ""
) {
    fun toIngredientEntity(): IngredientEntity {
        return IngredientEntity(
            name = name.trim(),
            amount = amount.toDoubleOrNull(),
            unit = unit.trim().takeIf { it.isNotEmpty() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    AddRecipeScreen(onSaveClick = {})
}
