package com.example.jobfinder.work

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.jobfinder.MainActivity
import com.example.jobfinder.R
import com.example.jobfinder.data.NotificationDatabase
import com.example.jobfinder.model.NotificationEntry
import kotlinx.coroutines.runBlocking

class JobReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    companion object {
        private const val TAG = "JobReminderWorker"
        private const val CHANNEL_ID = "job_reminder_channel"
        private const val NOTIFICATION_ID = 1001
    }

    override fun doWork(): Result {
        createNotificationChannel()
        showReminderNotification()
        logNotificationInDatabase()
        return Result.success()
    }

    private fun logNotificationInDatabase() {
        val db = NotificationDatabase.getInstance(applicationContext)
        val dao = db.notificationDao()

        runBlocking {
            dao.insert(
                NotificationEntry(
                    title = "Time to check for new jobs",
                    message = "Open JobFinder and see what’s new today.",
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    @SuppressLint("MissingPermission") // we manually check permission below
    private fun showReminderNotification() {
        val context = applicationContext

        // For Android 13+ we MUST have POST_NOTIFICATIONS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                Log.d(TAG, "POST_NOTIFICATIONS not granted, skipping notification")
                return
            }
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE else 0)
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Time to check for new jobs")
            .setContentText("Open JobFinder and see what’s new today.")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Job reminders"
            val descriptionText = "Reminders to check for new job opportunities"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
