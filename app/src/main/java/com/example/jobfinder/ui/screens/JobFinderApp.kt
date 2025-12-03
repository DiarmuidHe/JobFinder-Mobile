// JobFinderApp.kt
package com.example.jobfinder.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.JobFinderViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object JobDetail : Screen("jobDetail/{jobId}") {
        fun createRoute(jobId: String) = "jobDetail/$jobId"
    }
}

@Composable
fun JobFinderApp(
    // IMPORTANT: use your Factory so the VM can be constructed
    viewModel: JobFinderViewModel = viewModel(factory = JobFinderViewModel.Factory)
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                searchText = uiState.searchText,
                jobs = uiState.jobs,
                isSearching = uiState.isSearching,
                onSearchTextChange = { viewModel.onSearchTextChange(it) },
                onJobSelected = { job ->
                    // navigate to detail screen when a job is clicked
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
