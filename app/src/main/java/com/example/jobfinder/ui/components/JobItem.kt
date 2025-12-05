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
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteJob
//import com.example.jobfinder.ui.components.buttons.FavoriteButton
import androidx.compose.ui.unit.dp
import com.example.jobfinder.ui.components.buttons.FavoriteButton

@Composable
fun JobItem(
    modifier: Modifier = Modifier,
    jobTitle: String,
    company: String,
    location: String,
    onFavoriteJobClicked: (FavoriteJob) -> Unit,
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean,
    favoriteJob: FavoriteJob
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
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(jobTitle, style = MaterialTheme.typography.titleLarge)
                Text(company, style = MaterialTheme.typography.bodyLarge)
                Text(location, style = MaterialTheme.typography.bodyMedium)
            }
            FavoriteButton(
                onFavoriteJobClicked = onFavoriteJobClicked,
                isFavoriteButtonFilled = isFavoriteButtonFilled,
                favoriteJob = favoriteJob
            )
        }
    }
}
