package com.example.jobfinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.jobfinder.R
import com.example.jobfinder.model.Job

@Composable
fun JobInfoItem(
    job: Job,                             // Job object containing info to display
    modifier: Modifier = Modifier
) {
    // Card container for displaying brief job details
    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.padding_small)
                )
            ) {
                // Job title
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleLarge
                )
                // Company name
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.bodyLarge
                )
                // Job location
                Text(
                    text = job.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
