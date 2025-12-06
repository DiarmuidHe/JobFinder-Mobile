// JobFinderApp.kt
package com.example.jobfinder.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.JobFinderViewModel
import com.example.jobfinder.ui.screens.NotificationDashboardScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Notifications : Screen("notifications")
    data object JobDetail : Screen("jobDetail/{jobId}") {
        fun createRoute(jobId: String) = "jobDetail/$jobId"
    }

    // resume camera screen
    data object ResumeCamera : Screen("resumeCamera/{jobId}") {
        fun createRoute(jobId: String) = "resumeCamera/$jobId"
    }

    data object FinalApply : Screen("finalApply/{jobId}/{imageUri}") {
        fun createRoute(jobId: String, imageUri: String) =
            "finalApply/$jobId/$imageUri"
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobFinderApp(
    viewModel: JobFinderViewModel = viewModel(factory = JobFinderViewModel.Factory)
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Job Finder",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val bottomDestinations = listOf(
                Screen.Home,
                Screen.Favorites,
                Screen.Notifications
            )

            NavigationBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(10.dp)),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {
                bottomDestinations.forEach { screen ->
                    val selected = currentRoute == screen.route

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(screen.route) {
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
                                Screen.Notifications -> Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications"
                                )
                                Screen.JobDetail -> { /* not in bottom bar */ }
                                Screen.ResumeCamera -> TODO()
                                Screen.FinalApply -> TODO()
                            }
                        },
                        label = {
                            Text(
                                when (screen) {
                                    Screen.Home -> "Home"
                                    Screen.Favorites -> "Favorites"
                                    Screen.Notifications -> "Notifications"
                                    Screen.JobDetail -> ""
                                    Screen.ResumeCamera -> TODO()
                                    Screen.FinalApply -> TODO()
                                }
                            )
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
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

            // NOTIFICATIONS TAB
            composable(Screen.Notifications.route) {
                NotificationDashboardScreen()
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
                    onToggleFavorite = { viewModel.toggleFavoriteForJob(job) },
                    onApplyClick = {
                        navController.navigate(Screen.ResumeCamera.createRoute(job.id))
                    }
                )
            }
            // CAMERA FOR RESUME
            composable(
                route = Screen.ResumeCamera.route,
                arguments = listOf(
                    navArgument("jobId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getString("jobId")!!

                ResumeCameraScreen(
                    jobId = jobId,
                    onBack = { navController.popBackStack() },
                    onPhotoCaptured = { uri ->
                        // encode URI so it’s safe in the route
                        val encodedUri = Uri.encode(uri.toString())
                        navController.navigate(Screen.FinalApply.createRoute(jobId, encodedUri))
                    }
                )
            }
            composable(
                route = Screen.FinalApply.route,
                arguments = listOf(
                    navArgument("jobId") { type = NavType.StringType },
                    navArgument("imageUri") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getString("jobId")!!
                val imageUriStr = backStackEntry.arguments?.getString("imageUri")!!
                val imageUri = Uri.parse(Uri.decode(imageUriStr))

                // if you want the job info to show “Applying for XYZ”
                val job: Job? = uiState.jobs.firstOrNull { it.id == jobId }

                FinalApplyScreen(
                    job = job,
                    imageUri = imageUri,
                    onCancel = {
                        // back to Home, clearing back stack
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRetake = {
                        // go back to camera
                        navController.popBackStack()
                    },
                    onApply = {
                        // you could mark as applied here in DB if you want
                        // viewModel.markJobApplied(jobId)

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

        }
    }
}
