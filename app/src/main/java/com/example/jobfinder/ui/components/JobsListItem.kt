package com.example.jobfinder.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.Job

@Composable
fun JobsListItem(
    jobs: List<Job>,                         // List of jobs to display
    onJobSelected: (Job) -> Unit,            // Called when a user taps a job
    modifier: Modifier = Modifier
) {
    // Scrollable vertical list of job items
    LazyColumn(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_small)
        )
    ) {
        items(
            items = jobs,
            key = { it.id }                   // Stable key for list performance
        ) { job ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onJobSelected(job) }   // Handle item click
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                // Display job details inside each row
                JobInfoItem(job = job)
            }
        }
    }
}
