// JobFinderApp.kt
package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.JobFinderViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object JobDetail : Screen("jobDetail/{jobId}") {
        fun createRoute(jobId: String) = "jobDetail/$jobId"
    }
}


@Composable
fun JobFinderApp(
    viewModel: JobFinderViewModel = viewModel(factory = JobFinderViewModel.Factory)
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val bottomDestinations = listOf(Screen.Home, Screen.Favorites)

            NavigationBar {
                bottomDestinations.forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(screen.route) {
                                    // keep Home on back stack, restore state when switching tabs
                                    popUpTo(Screen.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            when (screen) {
                                Screen.Home -> Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home"
                                )
                                Screen.Favorites -> Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Favorites"
                                )
                                else -> {}
                            }
                        },
                        label = {
                            Text(
                                when (screen) {
                                    Screen.Home -> "Home"
                                    Screen.Favorites -> "Favorites"
                                    else -> ""
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            // HOME
            composable(Screen.Home.route) {
                HomeScreen(
                    searchText = uiState.searchText,
                    jobs = uiState.jobs,
                    isSearching = uiState.isSearching,
                    onSearchTextChange = { viewModel.onSearchTextChange(it) },
                    onJobSelected = { job ->
                        navController.navigate(Screen.JobDetail.createRoute(job.id))
                    },
                    favorites = uiState.favoriteJobs,
                    onFavoriteJobClicked = { favorite ->
                        viewModel.toggleFavorite(favorite)
                    },
                    isFavoriteButtonFilled = { favorite ->
                        viewModel.isFavoriteButtonFilled(favorite)
                    }
                )
            }

            // FAVORITES TAB
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    favorites = uiState.favoriteJobs,
                    onFavoriteJobClicked = { favorite ->
                        viewModel.toggleFavorite(favorite)
                    },
                    isFavoriteButtonFilled = { favorite ->
                        viewModel.isFavoriteButtonFilled(favorite)
                    }
                )
            }

            // DETAIL
            composable(
                route = Screen.JobDetail.route,
                arguments = listOf(
                    navArgument("jobId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getString("jobId")!!
                val job: Job = uiState.jobs.first { it.id == jobId }

                JobDetailScreen(
                    job = job,
                    isFavorite = viewModel.isJobFavorite(job),
                    onBackClick = { navController.popBackStack() },
                    onToggleFavorite = { viewModel.toggleFavoriteForJob(job) }
                )
            }
        }
    }
}