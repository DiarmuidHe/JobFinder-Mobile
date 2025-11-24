package com.example.jobfinder.ui.viewmodel

import com.example.jobfinder.model.Airport
import com.example.jobfinder.model.FavoriteRoute

data class FlightSearchUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val airports: List<Airport> = emptyList(),
    val isAirportSelected: Boolean = false,
    val selectedAirport: Airport? = null,
    var favoriteRoutes: List<FavoriteRoute> = emptyList(),
    val isOnboardingVisible: Boolean = false
)