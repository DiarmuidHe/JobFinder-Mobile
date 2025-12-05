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
    job: Job,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = job.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // no favorite button here – this is just for the Home list
        }
    }
}
