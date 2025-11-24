package com.example.jobfinder.ui

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
import android.util.Log
/**
 * ViewModel for JobFinder app
 */
class JobFinderViewModel(
    private val jobRepository: JobRepository,
    private val favoriteJobRepository: FavoriteJobRepository,
    private val userPreferencesRepository: SearchUserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JobFinderUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // restore last search
            onSearchTextChange(userPreferencesRepository.searchString.first())

            // load favorites
            updateFavoriteJobs()

            // onboarding
            if (!userPreferencesRepository.isOnboardingVisible.first()) {
                userPreferencesRepository.saveOnboardingVisibilityBooleanPreference(true)
                setOnboardingVisible()
            }
        }
    }

    // ---------------- SEARCH ----------------

    fun onSearchTextChange(text: String) =
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    searchText = text,
                    isSearching = true         // mark searching started
                )
            }

            userPreferencesRepository.saveSearchStringPreference(text)

            // small debounce
            delay(500)

            searchJobs(text)

            if (userPreferencesRepository.isOnboardingVisible.first()) {
                _uiState.update { state ->
                    state.copy(isOnboardingVisible = false)
                }
            }
        }

    private fun searchJobs(searchString: String) =
        viewModelScope.launch {
            jobRepository.getJobsByQuery("") // force "all jobs"
                .collect { jobs ->
                    android.util.Log.d("JobFinderVM", "DEBUG all jobs -> ${jobs.size}")
                    _uiState.update { state ->
                        state.copy(
                            jobs = jobs,
                            isSearching = false
                        )
                    }
                }
        }

    // ---------------- SELECTED JOB ----------------

    fun onJobClick(job: Job) {
        _uiState.update { state ->
            state.copy(selectedJob = job)
        }
    }

    // ---------------- FAVORITES ----------------

    fun isJobFavorite(favoriteJob: FavoriteJob): Boolean =
        _uiState.value.favoriteJobs.any { favorite ->
            favorite.jobId == favoriteJob.jobId
        }

    fun toggleFavorite(favoriteJob: FavoriteJob) =
        viewModelScope.launch {
            val isFavorite = isJobFavorite(favoriteJob)

            if (isFavorite) {
                // delete using its jobId
                favoriteJobRepository.deleteFavoriteJob(favoriteJob.jobId)
            } else {
                // insert directly
                favoriteJobRepository.insertFavoriteJob(
                    FavoriteJob(
                        jobId = favoriteJob.jobId,
                        title = favoriteJob.title,
                        company = favoriteJob.company,
                        location = favoriteJob.location
                    )
                )
            }

            updateFavoriteJobs()
        }

    private fun updateFavoriteJobs() =
        viewModelScope.launch {
            val favorites = favoriteJobRepository.getFavoriteJobs().first()
            _uiState.update { state ->
                state.copy(favoriteJobs = favorites)
            }
        }

    // ---------------- ONBOARDING ----------------

    private fun setOnboardingVisible() =
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isOnboardingVisible = true)
            }
        }

    // ---------------- FACTORY ----------------

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as JobFinderApplication)
                val jobRepository = application.jobRepository
                val favoriteJobRepository = application.favoriteJobRepository
                val userPreferencesRepository = application.userPreferencesRepository
                JobFinderViewModel(
                    jobRepository = jobRepository,
                    favoriteJobRepository = favoriteJobRepository,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}