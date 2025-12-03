// HomeScreen.kt
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.components.FavoriteJobsItem
import com.example.jobfinder.ui.components.JobSearchTextField
import com.example.jobfinder.ui.components.JobsListItem

@Composable
fun HomeScreen(
    searchText: String,
    jobs: List<Job>,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    onJobSelected: (Job) -> Unit,
    favorites: List<FavoriteJob>,
    onFavoriteJobClicked: (FavoriteJob) -> Unit,
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Top
        ) {
            JobSearchTextField(
                searchText = searchText,
                onSearchTextChange = onSearchTextChange
            )

            if (isSearching) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_large))
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                JobsListItem(
                    jobs = jobs,
                    onJobSelected = onJobSelected
                )

                AnimatedVisibility(
                    visible = favorites.isNotEmpty(),
                    enter = fadeIn(animationSpec = tween(300)) +
                            expandVertically(animationSpec = tween(300)),
                    exit = shrinkVertically(animationSpec = tween(200))
                ) {
                    Column(
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.padding_large)
                        )
                    ) {
                        FavoriteJobsItem(
                            favorites = favorites,
                            isFavoriteButtonFilled = isFavoriteButtonFilled,
                            onFavoriteJobClicked = onFavoriteJobClicked
                        )
                    }
                }
            }
        }
    }
}
