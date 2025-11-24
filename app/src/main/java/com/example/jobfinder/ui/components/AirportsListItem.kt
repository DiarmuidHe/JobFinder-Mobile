package com.example.jobfinder.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.model.Airport
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.fakeAirportsData

@Composable
fun AirportsListItem(
    modifier: Modifier = Modifier,
    airports: List<Airport>,
    onAirportSelected: (Airport) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(airports) { airport ->
            Row(modifier = Modifier
                .clickable { onAirportSelected(airport) }
            ) {
                AirportInfoItem(
                    modifier = Modifier,
                    iataCode = airport.iataCode,
                    name = airport.name
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun AirportsListItemPreview() {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            AirportsListItem(
                airports = fakeAirportsData,
                onAirportSelected = {}
            )
        }
    }
}