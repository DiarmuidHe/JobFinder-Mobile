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

private const val PREFERENCES_NAME = "job_finder_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class JobFinderApplication: Application() {
    private val database: JobFinderDatabase by lazy { JobFinderDatabase.getDatabase(this)

    }
    val jobRepository by lazy {
        OfflineJobRepository(database.jobDao())
    }
    val favoriteJobRepository by lazy {
        OfflineFavoriteJobRepository(database.favoriteJobDao())
    }

    lateinit var userPreferencesRepository: SearchUserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = SearchUserPreferencesRepository(dataStore)

        scheduleDailyJobReminder()
    }

    private fun scheduleDailyJobReminder() {
        // Example constraint: only run when device has *some* network
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
            ExistingPeriodicWorkPolicy.KEEP,   // don’t create duplicates
            dailyWorkRequest
        )
    }
}