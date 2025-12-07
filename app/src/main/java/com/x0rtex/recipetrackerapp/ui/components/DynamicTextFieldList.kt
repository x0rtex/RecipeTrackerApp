package com.x0rtex.recipetrackerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.x0rtex.recipetrackerapp.R

@Composable
fun DynamicTextFieldList(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemsChange: (List<String>) -> Unit,
    label: String = "Item",
    placeholder: String = stringResource(id = R.string.enter_label, label.lowercase()),
) {
    Column(modifier = modifier) {
        // Item label
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )

        // List of items
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(weight = 1f),
                    value = item,
                    onValueChange = { newValue ->
                        val updatedList = items.toMutableList()
                        updatedList[index] = newValue
                        onItemsChange(updatedList)
                    },
                    placeholder = { Text(text = "$placeholder ${index + 1}") },
                )

                IconButton(
                    onClick = {
                        val updatedList = items.toMutableList()
                        updatedList.removeAt(index)
                        onItemsChange(updatedList)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.remove)
                    )
                }
            }
        }

        // Add button
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onItemsChange(items + "")
            },
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            Spacer(Modifier.width(width = 8.dp))
            Text(text = stringResource(id = R.string.add_label, label))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DynamicTextFieldListPreview() {
    var items by remember { mutableStateOf(value = listOf("Step 1", "Step 2")) }
    DynamicTextFieldList(
        items = items,
        onItemsChange = { items = it },
        label = "Instructions"
    )
}
