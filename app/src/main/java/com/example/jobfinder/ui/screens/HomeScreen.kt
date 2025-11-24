package com.example.jobfinder.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jobfinder.R
import com.example.jobfinder.model.Job
import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.ui.components.JobItem
import com.example.jobfinder.ui.components.JobSearchTextField
import com.example.jobfinder.ui.components.JobSearchTitleItem
import com.example.jobfinder.ui.components.JobsListItem
import com.example.jobfinder.ui.components.FavoriteJobsItem
import com.example.jobfinder.ui.screens.WelcomeScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    jobs: List<Job>,
    favorites: List<FavoriteJob>,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    onJobSelected: (Job) -> Unit,
    selectedJob: Job?,
    onFavoriteJobClicked: (FavoriteJob) -> Unit,
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean,
    isOnboardingVisible: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {

        // Loading indicator
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        // Search bar
        JobSearchTextField(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange
        )

        // Show onboarding
        if (isOnboardingVisible) {
            WelcomeScreen()
        }

        // MAIN CONTENT
        if (searchText.isBlank()) {
            AnimatedVisibility(visible = true) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    if (favorites.isEmpty()) {
                        Text(
                            text = "No Saved Jobs",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        JobSearchTitleItem(text = "Saved Jobs")
                        FavoriteJobsItem(
                            favorites = favorites,
                            onFavoriteJobClicked = onFavoriteJobClicked,
                            isFavoriteButtonFilled = isFavoriteButtonFilled,
                            modifier = Modifier.animateEnterExit(
                                enter = expandVertically(animationSpec = tween(500)),
                                exit = shrinkVertically()
                            )
                        )
                    }
                }
            }
        } else {
            // SEARCH RESULTS LIST
            AnimatedVisibility(visible = true){
                JobsListItem(
                    jobs = jobs,
                    onJobSelected = onJobSelected,
                    modifier = Modifier.animateEnterExit(
                        enter = expandVertically(
                            animationSpec = tween(500),
                            expandFrom = Alignment.Top
                        ) + fadeIn(initialAlpha = 0.3f),
                        exit = shrinkVertically()
                    )
                )
            }

        }
    }
}
