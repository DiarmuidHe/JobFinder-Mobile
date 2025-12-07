package com.example.jobfinder.ui.viewmodel

import com.example.jobfinder.model.Job
import com.example.jobfinder.model.FavoriteJob

// JobFinderUiState.kt
data class JobFinderUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val jobs: List<Job> = emptyList(),

    // currently selected job (for the detail screen)
    val selectedJob: Job? = null,

    val favoriteJobs: List<FavoriteJob> = emptyList(),
)
