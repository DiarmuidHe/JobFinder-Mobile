package com.example.jobfinder.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.model.Job
import com.example.jobfinder.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon

// JobDetailSheet.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailSheet(
    job: Job,
    onDismiss: () -> Unit,
    onToggleFavorite: () -> Unit,
    isFavorite: Boolean
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(
                text = job.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Text(
                text = job.company,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = job.location,
                style = MaterialTheme.typography.bodyMedium
            )

            // TODO: later add more fields (description, salary, etc.), show them here.

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isFavorite) "Saved to favourites" else "Add to favourites",
                    style = MaterialTheme.typography.bodyLarge
                )

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

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close")
            }
        }
    }
}
