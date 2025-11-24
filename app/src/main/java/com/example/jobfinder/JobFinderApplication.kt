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
    }
}