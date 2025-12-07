// HomeScreen.kt
package com.example.jobfinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    searchText: String,                       // Current search query text
    jobs: List<Job>,                          // List of jobs from the database/search
    isSearching: Boolean,                     // Indicates if search results are loading
    onSearchTextChange: (String) -> Unit,     // Called when user types in the search box
    onJobSelected: (Job) -> Unit              // Called when user taps a job item
) {
    // Filter out jobs that the user has already applied for
    val visibleJobs = jobs.filter { !it.applied }

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

            // Search bar at the top of the screen
            JobSearchTextField(
                searchText = searchText,
                onSearchTextChange = onSearchTextChange
            )

            when {
                // Show a loading indicator while searching
                isSearching -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = dimensionResource(id = R.dimen.padding_large)),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // If there are no jobs, show an empty state message
                jobs.isEmpty() -> {
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

                // Otherwise, show list of jobs (excluding applied ones)
                else -> {
                    JobsListItem(
                        jobs = visibleJobs,
                        onJobSelected = onJobSelected
                    )
                }
            }
        }
    }
}

