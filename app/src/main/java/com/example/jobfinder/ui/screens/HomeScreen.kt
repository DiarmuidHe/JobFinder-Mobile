// HomeScreen.kt
package com.example.jobfinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.components.JobSearchTextField
import com.example.jobfinder.ui.components.JobsListItem

@Composable
fun HomeScreen(
    searchText: String,
    jobs: List<Job>,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    onJobSelected: (Job) -> Unit
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
            // search bar at top
            JobSearchTextField(
                searchText = searchText,
                onSearchTextChange = onSearchTextChange
            )

            when {
                isSearching -> {
                    // centered progress indicator under the search bar
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = dimensionResource(id = R.dimen.padding_large)),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CircularProgressIndicator()
                    }
                }

                jobs.isEmpty() -> {
                    // same style as Favorites empty state
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(id = R.dimen.padding_large)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No jobs found",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Try a different search term.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> {
                    // normal list
                    JobsListItem(
                        jobs = jobs,
                        onJobSelected = onJobSelected
                    )
                }
            }
        }
    }
}
