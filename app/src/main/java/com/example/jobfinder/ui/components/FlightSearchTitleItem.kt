package com.example.flightsearchapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.jobfinder.R
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.fakeAirportsData

@Composable
fun FlightSearchTitleItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_large),
                top = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_large)),
        text = text,
        color = MaterialTheme.colorScheme.inverseSurface,
        style = MaterialTheme.typography.titleMedium,
    )
}

@ThemePreviews
@Composable
fun FlightSearchTitleItemPreview() {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            FlightSearchTitleItem(
                text = stringResource(
                    id = R.string.flights_from, fakeAirportsData.first().iataCode
                )
            )
        }
    }
}