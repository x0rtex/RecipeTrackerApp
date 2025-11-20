package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
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
        shape = RoundedCornerShape(size = 12.dp)
    ) {
        Row(
            modifier = modifier,
        ) {
            Image(
                modifier = Modifier
                    .size(size = 72.dp)
                    .clip(shape = RoundedCornerShape(size = 10.dp)),
                painter = painterResource(id = recipe.image),
                contentDescription = null,
            )
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

@Preview
@Composable
fun RecipeCardPreview() {
    val recipe = RecipeEntity(
        id = 0,
        title = "Spaghetti Carbonara",
        description = "A classic Italian pasta dish.",
        ingredients = mutableListOf(),
        instructions = mutableListOf(),
        image = android.R.drawable.ic_menu_gallery,
        servings = 2
    )
    RecipeCard(
        recipe = recipe,
        onClick = {}
    )
}
