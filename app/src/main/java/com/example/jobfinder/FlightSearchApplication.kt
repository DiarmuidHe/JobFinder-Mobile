package com.example.jobfinder

import android.app.Application
import android.content.Context

import com.example.jobfinder.data.FlightSearchDatabase
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.jobfinder.data.OfflineAirportRepository
import com.example.jobfinder.data.OfflineFavoriteRouteRepository
import com.example.jobfinder.data.SearchUserPreferencesRepository

private const val PREFERENCES_NAME = "flight_search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class FlightSearchApplication: Application() {
    private val database: FlightSearchDatabase by lazy { FlightSearchDatabase.getDatabase(this) }

    val airportRepository by lazy {
        OfflineAirportRepository(database.airportDao())
    }
    val favoriteRouteRepository by lazy {
        OfflineFavoriteRouteRepository(database.favoriteRoutesDao())
    }

    lateinit var userPreferencesRepository: SearchUserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = SearchUserPreferencesRepository(dataStore)
    }
}