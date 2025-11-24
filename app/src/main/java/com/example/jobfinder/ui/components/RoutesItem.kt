package com.example.jobfinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteRoute
import com.example.jobfinder.ui.components.buttons.FavoriteButton
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.fakeAirportsData

@Composable
fun RouteItem(
    modifier: Modifier = Modifier,
    departureName: String,
    destinationName: String,
    departureIata: String,
    destinationIata: String,
    onFavoriteRouteClicked: (FavoriteRoute) -> Unit,
    isFavoriteButtonFilled: (FavoriteRoute) -> Boolean,
) {
    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        shape = RoundedCornerShape(topEndPercent = 15),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(id = R.string.depart).uppercase(),
                    style = MaterialTheme.typography.titleSmall
                )
                AirportInfoItem(
                    modifier = Modifier,
                    iataCode = departureIata,
                    name = departureName,
                )
                Text(
                    text = stringResource(id = R.string.arrive).uppercase(),
                    style = MaterialTheme.typography.titleSmall
                )
                AirportInfoItem(
                    modifier = Modifier,
                    iataCode = destinationIata,
                    name = destinationName
                )
            }
            FavoriteButton(
                onFavoriteRouteClicked = onFavoriteRouteClicked,
                isFavoriteButtonFilled = isFavoriteButtonFilled,
                route = FavoriteRoute(
                    departureIata = departureIata,
                    destinationIata = destinationIata,
                )
            )
        }
    }
}


@ThemePreviews
@Composable
fun RouteItemPreview() {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            RouteItem(
                departureIata = fakeAirportsData.first().iataCode,
                destinationIata = fakeAirportsData.last().iataCode,
                departureName = fakeAirportsData.first().name,
                destinationName = fakeAirportsData.last().name,
                onFavoriteRouteClicked = {},
                isFavoriteButtonFilled = { true }
            )
        }
    }
}
