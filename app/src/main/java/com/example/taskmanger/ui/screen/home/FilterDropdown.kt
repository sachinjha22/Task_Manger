package com.example.taskmanger.ui.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.taskmanger.data.model.filter.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    selectedFilter: TaskStatus,
    modifier: Modifier = Modifier,
    onFilterSelected: (TaskStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = TaskStatus.entries.toTypedArray()

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selectedFilter.name.replace("_", " "),
            onValueChange = {},
            readOnly = true,
            label = { Text("Filter By") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name.replace("_", " ")) },
                    onClick = {
                        onFilterSelected(option)
                        expanded = false
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}
