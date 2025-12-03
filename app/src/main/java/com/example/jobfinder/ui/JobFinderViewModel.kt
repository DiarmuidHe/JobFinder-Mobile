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

    private val _uiState = MutableStateFlow(JobFinderUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // restore last search
            onSearchTextChange(userPreferencesRepository.searchString.first())

            // load favorites
            updateFavoriteJobs()

            // onboarding
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

    // CHANGED: make this suspend instead of launching a new coroutine inside
    private suspend fun searchJobs(searchString: String) {
        jobRepository.getJobsByQuery(query = searchString)
            .collect { jobs ->
                Log.d("JobFinderVM", "search '$searchString' -> ${jobs.size} jobs")
                _uiState.update { state ->
                    state.copy(
                        jobs = jobs,
                        isSearching = false       // done searching
                    )
                }
            }
    }

    // ---------------- SELECTED JOB / DETAIL ----------------

    // CHANGED: when a job is clicked, also show the detail view
    fun onJobClick(job: Job) {
        _uiState.update { state ->
            state.copy(
                selectedJob = job,
                isJobDetailVisible = true        // <--- OPEN DETAIL
            )
        }
    }

    // NEW: call this when the detail screen/bottom sheet is dismissed
    fun onJobDetailDismissed() {
        _uiState.update { state ->
            state.copy(
                selectedJob = null,
                isJobDetailVisible = false        // <--- CLOSE DETAIL
            )
        }
    }

    // ---------------- FAVORITES (LIST-LEVEL) ----------------

    // unchanged: used when you already have a FavoriteJob object (e.g. in the favorites list)
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
            Log.d("JobFinderVM", "Favorites from DB: ${favorites.map { it.jobId }}")
            _uiState.update { state ->
                state.copy(favoriteJobs = favorites)
            }
        }

    fun isFavoriteButtonFilled(favoriteJob: FavoriteJob): Boolean {
        return uiState.value.favoriteJobs.any { it.jobId == favoriteJob.jobId }
    }

    // ---------------- FAVORITES (DETAIL SCREEN) ----------------
    // NEW: same idea, but using a Job instead of FavoriteJob so the detail screen can use it directly.

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
                        location = job.location
                    )
                )
            }

            updateFavoriteJobs()
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
