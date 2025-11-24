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
    modifier: Modifier = Modifier,
    jobs: List<Job>,
    onJobSelected: (Job) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(jobs) { job ->
            Row(modifier = Modifier
                .clickable { onJobSelected(job) }
            ) {
                JobInfoItem(
                    modifier = Modifier,
                    job = job
                )
            }
        }
    }
}
