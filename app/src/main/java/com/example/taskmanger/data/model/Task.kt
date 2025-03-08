package com.example.taskmanger.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val priority: Priority,
    val dueDate: Date?,
    val isCompleted: Boolean = false
) : Parcelable

enum class Priority { LOW, MEDIUM, HIGH }
