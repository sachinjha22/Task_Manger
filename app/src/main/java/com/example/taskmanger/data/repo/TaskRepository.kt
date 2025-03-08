package com.example.taskmanger.data.repo

import com.example.taskmanger.data.db.taskdb.TaskDao
import com.example.taskmanger.data.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun updateTasks(tasks: List<Task>) = taskDao.updateTasks(tasks)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    fun getTaskById(taskId: Int): Flow<Task?> = taskDao.getTaskById(taskId)
}