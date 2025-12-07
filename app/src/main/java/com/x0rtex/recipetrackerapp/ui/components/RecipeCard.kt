package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity

@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(size = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
        ) {
            // Recipe Card Image
            RecipeCardImage(
                imageUri = recipe.image,
                contentDescription = recipe.title,
                modifier = Modifier.size(size = 96.dp)
            )

            // Recipe Card Info
            Column(
                modifier = modifier,
            ) {
                Text(
                    modifier = modifier,
                    text = recipe.title,
                )
                Text(
                    modifier = modifier,
                    text = recipe.description,
                )
            }
        }
    }
}

@Composable
private fun RecipeCardImage(
    imageUri: String?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                // Load image
                AsyncImage(
                    model = imageUri.toUri(),
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Show placeholder
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(size = 36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun RecipeCardPreview() {
    val recipe = RecipeEntity(
        id = 0,
        title = "Spaghetti Carbonara",
        description = "A classic Italian pasta dish.",
        ingredients = mutableListOf(),
        instructions = mutableListOf(),
        servings = 2
    )
    RecipeCard(
        recipe = recipe,
        onClick = {}
    )
}
