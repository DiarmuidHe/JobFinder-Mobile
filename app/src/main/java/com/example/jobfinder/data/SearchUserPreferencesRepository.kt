package com.example.jobfinder.data

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SearchUserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCH_STRING = stringPreferencesKey("search_string")
        val ONBOARDING_VISIBLE = booleanPreferencesKey("onboarding_visible")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveSearchStringPreference(searchString: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[SEARCH_STRING] = searchString
        }
    }

    suspend fun saveOnboardingVisibilityBooleanPreference(isOnboardingVisible: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[ONBOARDING_VISIBLE] = isOnboardingVisible
        }
    }

    val isOnboardingVisible: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_VISIBLE] ?: false
        }

    val searchString: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
        }
        .map { preferences ->
            preferences[SEARCH_STRING] ?: ""
        }
}