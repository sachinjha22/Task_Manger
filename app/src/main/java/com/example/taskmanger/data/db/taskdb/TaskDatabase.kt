package com.example.taskmanger.data.db.taskdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanger.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
