package com.x0rtex.recipetrackerapp.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.x0rtex.recipetrackerapp.R
import com.x0rtex.recipetrackerapp.data.models.RecipeEntity
import com.x0rtex.recipetrackerapp.ui.components.RecipeCard
import com.x0rtex.recipetrackerapp.ui.theme.RecipeTrackerAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    recipes: List<RecipeEntity>,
    searchQuery: String = "",
    selectedTag: String? = null,
    availableTags: List<String> = emptyList(),
    onSearchQueryChange: (String) -> Unit = {},
    onTagSelect: (String?) -> Unit = {},
    onRecipeClick: (RecipeEntity) -> Unit
) {
    Column(modifier = modifier) {
        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Tag Filter Chips
        if (availableTags.isNotEmpty()) {
            TagFilterRow(
                tags = availableTags,
                selectedTag = selectedTag,
                onTagSelect = onTagSelect,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Recipe List
        if (recipes.isEmpty()) {
            EmptyRecipeList(
                hasFilters = searchQuery.isNotEmpty() || selectedTag != null
            )
        } else {
            LazyColumn {
                items(items = recipes, key = { it.id }) { recipe ->
                    RecipeCard(
                        modifier = Modifier.padding(bottom = 8.dp),
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe) }
                    )
                }
            }
        }
    }
}

// Search bar for recipes
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text(text = stringResource(id = R.string.search_recipes)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clear_search)
                    )
                }
            }
        },
        singleLine = true
    )
}

// Row of FilterChips
@Composable
private fun TagFilterRow(
    tags: List<String>,
    selectedTag: String?,
    onTagSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = stringResource(id = R.string.filter),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size = 20.dp)
        )

        FilterChip(
            selected = selectedTag == null,
            onClick = { onTagSelect(null) },
            label = { Text(text = stringResource(id = R.string.all)) }
        )

        tags.forEach { tag ->
            FilterChip(
                selected = selectedTag == tag,
                onClick = {
                    onTagSelect(if (selectedTag == tag) null else tag)
                },
                label = { Text(text = tag) }
            )
        }
    }
}

// Empty recipe list
@Composable
private fun EmptyRecipeList(
    hasFilters: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (hasFilters) {
                stringResource(id = R.string.no_recipes_found)
            } else {
                stringResource(id = R.string.no_recipes_yet)
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RecipeTrackerAppTheme {
        HomeScreen(
            recipes = listOf(
                RecipeEntity(
                    id = 1,
                    title = "Chocolate Cake",
                    description = "A delicious chocolate cake",
                ),
                RecipeEntity(
                    id = 2,
                    title = "Pasta Carbonara",
                    description = "Classic Italian pasta",
                )
            ),
            availableTags = listOf("baking", "cooking"),
            onRecipeClick = {}
        )
    }
}