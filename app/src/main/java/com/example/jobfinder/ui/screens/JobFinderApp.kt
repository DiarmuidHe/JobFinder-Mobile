package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobfinder.ui.components.JobSearchTopAppBarPreview

import com.example.jobfinder.ui.JobFinderViewModel

// JobFinderApp.kt

@Composable
fun JobFinderApp(
    modifier: Modifier = Modifier
) {
    val viewModel: JobFinderViewModel = viewModel(factory = JobFinderViewModel.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { JobSearchTopAppBarPreview() }
    ) { innerPadding ->
        Surface(
            modifier = modifier.padding(innerPadding)
        ) {
            HomeScreen(
                searchText = uiState.searchText,
                jobs = uiState.jobs,
                isSearching = uiState.isSearching,
                onSearchTextChange = viewModel::onSearchTextChange,

                selectedJob = uiState.selectedJob,
                onJobSelected = viewModel::onJobClick,

                favorites = uiState.favoriteJobs,
                onFavoriteJobClicked = viewModel::toggleFavorite,
                isFavoriteButtonFilled = viewModel::isFavoriteButtonFilled,

                isOnboardingVisible = uiState.isOnboardingVisible,

                // NEW:
                isJobDetailVisible = uiState.isJobDetailVisible,
                onJobDetailDismissed = viewModel::onJobDetailDismissed,
                onToggleFavoriteFromDetail = viewModel::toggleFavoriteForJob,
                isJobFavorite = viewModel::isJobFavorite
            )
        }
    }
}
