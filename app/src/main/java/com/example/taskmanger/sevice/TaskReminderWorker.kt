package com.example.taskmanger.sevice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.taskmanger.R
import java.security.SecureRandom

class TaskReminderWorker(appContext: Context, params: WorkerParameters) :
    Worker(appContext, params) {
    override fun doWork(): Result {
        sendNotification()
        Log.d("VVV", "result ${Result.success()}")
        return Result.success()
    }

    private fun sendNotification() {
        try {
            val builder = NotificationCompat.Builder(applicationContext, "task_channel")
                .setContentTitle("Task Reminder")
                .setContentText("You have pending tasks!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)

            builder.color = ContextCompat.getColor(applicationContext, R.color.background)

            val notification = builder.build()
            val notificationManager =
                ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "task_channel", "task", NotificationManager.IMPORTANCE_HIGH
                )
                channel.setShowBadge(true)
                channel.canShowBadge()
                notificationManager?.createNotificationChannel(
                    channel
                )
            }
            notification.flags = Notification.FLAG_AUTO_CANCEL
            val notifyID = SecureRandom().nextInt(99999999) + 1
            notificationManager?.notify(notifyID, notification)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
