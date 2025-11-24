package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobfinder.ui.components.FlightSearchTopAppBar
import com.example.jobfinder.ui.viewmodel.FlightSearchViewModel
import com.example.jobfinder.utils.emptyAirportData

@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier
) {
    val viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.Factory)
    val flightSearchUiState = viewModel.flightSearchUiState.collectAsState()
    val arrivalsForSelectedAirport by viewModel.getArrivalsForSelectedAirport(
        selectedAirport = flightSearchUiState.value.selectedAirport ?: emptyAirportData
    ).collectAsState(emptyList())

    Scaffold(
        topBar = { FlightSearchTopAppBar() },
        content = { contentPadding ->
            Surface {
                HomeScreen(
                    modifier = modifier.padding(contentPadding),
                    searchText = flightSearchUiState.value.searchText,
                    airports = flightSearchUiState.value.airports,
                    isSearching = flightSearchUiState.value.isSearching,
                    onSearchTextChange = viewModel::onSearchTextChange,
                    isAirportSelected = flightSearchUiState.value.isAirportSelected,
                    onAirportSelected = viewModel::onAirportClick,
                    selectedAirport = flightSearchUiState.value.selectedAirport,
                    arrivalsForSelectedAirport = arrivalsForSelectedAirport,
                    onFavoriteRouteClicked = viewModel::markOrUnmarkAsFavorite,
                    isFavoriteButtonFilled = viewModel::validateFavorite,
                    favorites = flightSearchUiState.value.favoriteRoutes,
                    isOnboardingVisible = flightSearchUiState.value.isOnboardingVisible
                )
            }
        }
    )
}