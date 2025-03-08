package com.example.taskmanger.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmanger.data.model.Task
import com.example.taskmanger.data.model.filter.SortOption
import com.example.taskmanger.data.model.filter.TaskStatus
import com.example.taskmanger.ui.component.TaskItem
import com.example.taskmanger.utils.Resource
import com.example.taskmanger.viewmodel.TaskViewModel


@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TaskViewModel
) {
    val tasks by viewModel.tasks.collectAsState()
    var selectedFilter by remember { mutableStateOf(TaskStatus.ALL) }
    var selectedSort by remember { mutableStateOf(SortOption.BY_DATE) }
    var draggedItem by remember { mutableStateOf<Task?>(null) }


    Box(modifier = Modifier.fillMaxSize()) {

        when (tasks) {
            is Resource.Loading -> {
                LazyColumn {
                    items(5) { ShimmerScreen() } // Show shimmer effect
                }
            }

            is Resource.Success -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(10.dp))
                            SortDropdown(selectedSort) { selectedSort = it }
                            Spacer(modifier = Modifier.height(8.dp))
                            FilterDropdown(selectedFilter) { selectedFilter = it }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    val filteredSortedTasks =
                        viewModel.getFilteredSortedTasks(
                            selectedFilter,
                            selectedSort
                        )   // Filtering and sorting task are performing here
                    if (filteredSortedTasks.isNotEmpty()) {
                        itemsIndexed(
                            filteredSortedTasks,
                            key = { _, task -> task.id }) { index, task ->
                            TaskItem(
                                task = task,
                                onTaskClick = { navController.navigate("taskDetails/${task.id}") },
                                onCompleteToggle = { viewModel.toggleComplete(it) },
                                onDeleteClick = { viewModel.deleteTask(it) },
                                onTaskReorder = { fromIndex, toIndex ->
                                    viewModel.reorderTasks(fromIndex, toIndex)
                                },
                                index = index,  // Pass index to TaskItem
                                onDragStart = { draggedItem = task },
                                onDragEnd = { draggedItem = null }
                            )
                        }
                    } else {  // if we don't have task
                        item {
                            EmptyStateUI(
                                "No tasks available. with $selectedFilter and ${
                                    selectedSort.toString().replace(
                                        "_",
                                        " "
                                    )
                                }"
                            )
                        }

                    }
                }
            }

            is Resource.Error -> {
                EmptyStateUI("No tasks yet! Start adding tasks and stay productive")
            }
        }
    }
}
