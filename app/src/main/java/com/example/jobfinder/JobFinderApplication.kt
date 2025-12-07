package com.example.jobfinder

import android.app.Application
import android.content.Context

import com.example.jobfinder.data.JobFinderDatabase
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.jobfinder.data.OfflineJobRepository
import com.example.jobfinder.data.OfflineFavoriteJobRepository
import com.example.jobfinder.data.SearchUserPreferencesRepository
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.jobfinder.work.JobReminderWorker
import java.util.concurrent.TimeUnit

// Name for the app’s DataStore preferences file
private const val PREFERENCES_NAME = "job_finder_preferences"

// Extension property to create a single DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class JobFinderApplication : Application() {

    // Single instance of Room database for the app
    private val database: JobFinderDatabase by lazy {
        JobFinderDatabase.getDatabase(this)
    }

    // Repository for job data (from local DB)
    val jobRepository by lazy {
        OfflineJobRepository(database.jobDao())
    }

    // Repository for favorite jobs
    val favoriteJobRepository by lazy {
        OfflineFavoriteJobRepository(database.favoriteJobDao())
    }

    // Repository for user search preferences (DataStore)
    lateinit var userPreferencesRepository: SearchUserPreferencesRepository

    override fun onCreate() {
        super.onCreate()

        // Initialize preferences repository with DataStore
        userPreferencesRepository = SearchUserPreferencesRepository(dataStore)

        // Schedule background worker for daily job reminders
        scheduleDailyJobReminder()
    }

    // Set up a periodic WorkManager task to send daily job reminders
    private fun scheduleDailyJobReminder() {
        // Only run when device has a network connection
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Run once every 24 hours
        val dailyWorkRequest =
            PeriodicWorkRequestBuilder<JobReminderWorker>(24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyJobReminder",
            ExistingPeriodicWorkPolicy.KEEP,   // Reuse existing work if already scheduled
            dailyWorkRequest
        )
    }
}
