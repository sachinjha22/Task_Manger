package com.example.taskmanger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanger.data.model.Task
import com.example.taskmanger.data.model.filter.SortOption
import com.example.taskmanger.data.model.filter.TaskStatus
import com.example.taskmanger.data.repo.TaskRepository
import com.example.taskmanger.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<Resource<List<Task>>>(Resource.Loading)
    val tasks: StateFlow<Resource<List<Task>>> get() = _tasks
    private val _tasksData = MutableStateFlow<List<Task>>(emptyList())
    val tasksData: StateFlow<List<Task>> get() = _tasksData

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { taskList ->
                _tasks.value = if (taskList.isNotEmpty()) {
                    _tasksData.value = taskList
                    Resource.Success(taskList)
                } else {
                    Resource.Error("NDF")
                }
            }
        }
    }


//    val tasks: Flow<List<Task>> = repository.getAllTasks()

    fun insertTask(task: Task) = viewModelScope.launch { repository.insertTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repository.deleteTask(task) }
    fun toggleComplete(task: Task) = viewModelScope.launch {
        repository.updateTask(task.copy(isCompleted = !task.isCompleted))
    }

    fun getTaskById(taskId: Int): Flow<Task?> = repository.getTaskById(taskId)

    fun getFilteredSortedTasks(status: TaskStatus, sortOption: SortOption): List<Task> {
        return _tasksData.value.filter { task ->
            when (status) {
                TaskStatus.ALL -> true
                TaskStatus.COMPLETED -> task.isCompleted
                TaskStatus.PENDING -> !task.isCompleted
            }
        }.sortedWith(
            when (sortOption) {
                SortOption.BY_DATE -> compareBy { it.dueDate }
                SortOption.BY_PRIORITY -> compareByDescending { it.priority }
                SortOption.ALPHABETICAL -> compareBy { it.title }
            }
        )
    }

    fun reorderTasks(fromIndex: Int, toIndex: Int) {
        viewModelScope.launch {
            val currentList = _tasksData.value.toMutableList()
            val movedTask = currentList.removeAt(fromIndex)
            currentList.add(toIndex, movedTask)

            // Update database with new order
            repository.updateTasks(currentList)

            // Update state
            _tasksData.value = currentList
        }
    }
}