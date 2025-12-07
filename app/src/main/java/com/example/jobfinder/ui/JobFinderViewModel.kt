package com.example.jobfinder.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jobfinder.JobFinderApplication
import com.example.jobfinder.data.FavoriteJobRepository
import com.example.jobfinder.data.JobRepository
import com.example.jobfinder.data.SearchUserPreferencesRepository
import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.viewmodel.JobFinderUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for JobFinder app
 */
class JobFinderViewModel(
    private val jobRepository: JobRepository,
    private val favoriteJobRepository: FavoriteJobRepository,
    private val userPreferencesRepository: SearchUserPreferencesRepository
) : ViewModel() {

    // UI state exposed as StateFlow to the UI
    private val _uiState = MutableStateFlow(JobFinderUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Restore last search text from DataStore
            onSearchTextChange(userPreferencesRepository.searchString.first())

            // Load existing favorites from DB
            updateFavoriteJobs()
        }
    }

    //  SEARCH

    // Called whenever the search text changes
    fun onSearchTextChange(text: String) =
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    searchText = text,
                    isSearching = true         // mark searching as started
                )
            }

            // Persist the last search query
            userPreferencesRepository.saveSearchStringPreference(text)

            // Small debounce so we don't search on every keystroke immediately
            delay(500)

            searchJobs(text)
        }

    // Runs the job search and updates the UI state
    private suspend fun searchJobs(searchString: String) {
        jobRepository.getJobsByQuery(query = searchString)
            .collect { jobs ->
                Log.d("JobFinderVM", "search '$searchString' -> ${jobs.size} jobs")
                _uiState.update { state ->
                    state.copy(
                        jobs = jobs,
                        isSearching = false       // search finished
                    )
                }
            }
    }

    //FAVORITES (LIST & CARD)

    // Check if a FavoriteJob is in the current favorites list
    fun isJobFavorite(favoriteJob: FavoriteJob): Boolean =
        _uiState.value.favoriteJobs.any { favorite ->
            favorite.jobId == favoriteJob.jobId
        }

    // Toggle favorite from a FavoriteJob item (e.g. favorites list)
    fun toggleFavorite(favoriteJob: FavoriteJob) =
        viewModelScope.launch {
            val isFavorite = isJobFavorite(favoriteJob)

            if (isFavorite) {
                // Remove from favorites using the jobId
                favoriteJobRepository.deleteFavoriteJob(favoriteJob.jobId)
            } else {
                // Insert a new favorite (let Room auto-generate the ID)
                favoriteJobRepository.insertFavoriteJob(
                    favoriteJob.copy(
                        id = null
                    )
                )
            }

            // Refresh favorites from DB
            updateFavoriteJobs()
        }

    // Reload favorites from DB and update UI state
    private fun updateFavoriteJobs() =
        viewModelScope.launch {
            val favorites = favoriteJobRepository.getFavoriteJobs().first()
            Log.d("JobFinderVM", "Favorites from DB: ${favorites.map { it.jobId }}")
            _uiState.update { state ->
                state.copy(favoriteJobs = favorites)
            }
        }

    // Helper for the favorite button in job cards
    fun isFavoriteButtonFilled(favoriteJob: FavoriteJob): Boolean {
        return uiState.value.favoriteJobs.any { it.jobId == favoriteJob.jobId }
    }

    // FAVORITES (DETAIL SCREEN)
    // Same logic as above but starting from a Job instead of FavoriteJob

    fun isJobFavorite(job: Job): Boolean =
        _uiState.value.favoriteJobs.any { favorite ->
            favorite.jobId == job.id
        }

    fun toggleFavoriteForJob(job: Job) =
        viewModelScope.launch {
            Log.d("JobFinderVM", "toggleFavoriteForJob called for job.id=${job.id}")
            val isFavorite = isJobFavorite(job)

            if (isFavorite) {
                Log.d("JobFinderVM", "Currently favorite -> deleting")
                favoriteJobRepository.deleteFavoriteJob(job.id)
            } else {
                Log.d("JobFinderVM", "Currently NOT favorite -> inserting")
                favoriteJobRepository.insertFavoriteJob(
                    FavoriteJob(
                        jobId = job.id,
                        title = job.title,
                        company = job.company,
                        location = job.location,
                        description = job.description,
                        skills = job.skills,
                        image = job.image
                    )
                )
            }

            // Refresh favorites after change
            updateFavoriteJobs()
        }

    // APPLIED STATE

    // Mark a job as applied in both DB and in-memory UI state
    fun markJobAsApplied(jobId: String) =
        viewModelScope.launch {
            // Update DB record
            jobRepository.updateApplied(jobId, true)

            // Update current UI state so the change is reflected instantly
            _uiState.update { state ->
                state.copy(
                    jobs = state.jobs.map { job ->
                        if (job.id == jobId) job.copy(applied = true) else job
                    },
                    selectedJob = state.selectedJob?.let { selected ->
                        if (selected.id == jobId) selected.copy(applied = true) else selected
                    }
                )
            }
        }

    //FACTORY

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as JobFinderApplication)

                // Get dependencies from Application
                val jobRepository = application.jobRepository
                val favoriteJobRepository = application.favoriteJobRepository
                val userPreferencesRepository = application.userPreferencesRepository

                // Create ViewModel instance with injected repositories
                JobFinderViewModel(
                    jobRepository = jobRepository,
                    favoriteJobRepository = favoriteJobRepository,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}
