package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    job: Job,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(job.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (isFavorite)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(
                text = job.company,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = job.location,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))

            // TODO: description, salary, etc.

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close")
            }
        }
    }
}
