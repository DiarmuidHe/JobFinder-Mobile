package com.example.jobfinder.ui.viewmodel

import com.example.jobfinder.model.Job
import com.example.jobfinder.model.FavoriteJob

// JobFinderUiState.kt
data class JobFinderUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val jobs: List<Job> = emptyList(),

    // NEW: currently selected job (for the detail screen)
    val selectedJob: Job? = null,

    val favoriteJobs: List<FavoriteJob> = emptyList(),
    val isOnboardingVisible: Boolean = false,

    // NEW: whether the detail screen is visible
    val isJobDetailVisible: Boolean = false
)
