package com.example.jobfinder.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.jobfinder.R
import com.example.jobfinder.model.Airport
import com.example.jobfinder.model.FavoriteRoute
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.fakeAirportsData

@Composable
fun RoutesForSelectedAirportItem(
    arrivalsForSelectedAirport: List<Airport>,
    selectedAirport: Airport,
    onFavoriteRouteClicked: (FavoriteRoute) -> Unit,
    isFavoriteButtonFilled: (FavoriteRoute) -> Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        items(arrivalsForSelectedAirport) { destinationAirport ->
            RouteItem(
                modifier = modifier,
                departureName = selectedAirport.name,
                destinationName = destinationAirport.name,
                departureIata = selectedAirport.iataCode,
                destinationIata = destinationAirport.iataCode,
                onFavoriteRouteClicked = onFavoriteRouteClicked,
                isFavoriteButtonFilled = isFavoriteButtonFilled
            )
        }
    }
}

@ThemePreviews
@Composable
fun RoutesForSelectedAirportItemPreview(
) {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            RoutesForSelectedAirportItem(
                arrivalsForSelectedAirport = fakeAirportsData,
                selectedAirport = fakeAirportsData.first(),
                onFavoriteRouteClicked = {},
                isFavoriteButtonFilled = { true },
                modifier = Modifier
            )
        }
    }
}
