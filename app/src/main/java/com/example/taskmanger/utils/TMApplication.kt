package com.example.taskmanger.utils

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskmanger.sevice.TaskReminderWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class TMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val workRequest =
            PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}