package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobfinder.ui.components.JobSearchTopAppBarPreview

import com.example.jobfinder.ui.JobFinderViewModel

@Composable
fun JobFinderApp(
    modifier: Modifier = Modifier
) {
    val viewModel: JobFinderViewModel = viewModel(factory = JobFinderViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { JobSearchTopAppBarPreview() }
    ) { contentPadding ->
        Surface {
            HomeScreen(
                modifier = modifier.padding(contentPadding),
                searchText = uiState.value.searchText,
                jobs = uiState.value.jobs,
                isSearching = uiState.value.isSearching,
                onSearchTextChange = viewModel::onSearchTextChange,
                selectedJob = uiState.value.selectedJob,
                onJobSelected = viewModel::onJobClick,
                favorites = uiState.value.favoriteJobs,
                onFavoriteJobClicked = viewModel::toggleFavorite,
                isFavoriteButtonFilled = viewModel::isJobFavorite,
                isOnboardingVisible = uiState.value.isOnboardingVisible
            )
        }
    }
}
