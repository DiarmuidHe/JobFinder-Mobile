package com.example.jobfinder.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteJob

@Composable
fun FavoriteJobsItem(
    modifier: Modifier = Modifier,
    favorites: List<FavoriteJob>,
    onFavoriteJobClicked: (FavoriteJob) -> Unit,
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean
) {
    LazyColumn(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) { favorite ->
            JobItem(
                jobTitle = favorite.title,
                company = favorite.company,
                location = favorite.location,
                onFavoriteJobClicked = onFavoriteJobClicked,
                isFavoriteButtonFilled = isFavoriteButtonFilled,
                favoriteJob = favorite
            )
        }
    }
}
