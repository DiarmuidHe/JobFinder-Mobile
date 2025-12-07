package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.ui.components.FavoriteJobsItem

@Composable
fun FavoritesScreen(
    favorites: List<FavoriteJob>,                     // List of all user’s favorited jobs
    onFavoriteJobClicked: (FavoriteJob) -> Unit,      // Handles favorite/unfavorite action
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean  // Checks if a job is currently favorited
) {
    // Show an empty state message when no favorites exist
    if (favorites.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No favorites yet",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tap the star on a job to save it here.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        // Display list of favorited jobs
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            FavoriteJobsItem(
                favorites = favorites,
                isFavoriteButtonFilled = isFavoriteButtonFilled,
                onFavoriteJobClicked = onFavoriteJobClicked
            )
        }
    }
}