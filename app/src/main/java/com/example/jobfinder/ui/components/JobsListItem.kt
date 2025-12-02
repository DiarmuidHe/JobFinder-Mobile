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
    jobs: List<Job>,
    onJobSelected: (Job) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(jobs) { job ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onJobSelected(job) }
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                JobInfoItem(job = job)
            }
        }
    }
}