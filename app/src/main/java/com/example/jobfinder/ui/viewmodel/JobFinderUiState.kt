package com.example.jobfinder.ui.viewmodel

import com.example.jobfinder.model.Job
import com.example.jobfinder.model.FavoriteJob

data class JobFinderUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val jobs: List<Job> = emptyList(),
    val selectedJob: Job? = null,
    val favoriteJobs: List<FavoriteJob> = emptyList(),
    val isOnboardingVisible: Boolean = false
)