package com.example.jobfinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.jobfinder.model.FavoriteRoute
import com.example.jobfinder.ui.components.buttons.FavoriteButton
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.fakeAirportsData

@Composable
fun AirportInfoItem(
    modifier: Modifier,
    iataCode: String,
    name: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = iataCode,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
        )
        Text(
            text = name,
            style = MaterialTheme.typography.displayMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@ThemePreviews
@Composable
fun AirportInfoItemPreview() {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            AirportInfoItem(
                iataCode = fakeAirportsData.first().iataCode,
                name = fakeAirportsData.first().name,
                modifier = Modifier
            )
        }
    }
}
