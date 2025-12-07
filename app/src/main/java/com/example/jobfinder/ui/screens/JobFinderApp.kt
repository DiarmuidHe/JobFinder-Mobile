// JobFinderApp.kt
package com.example.jobfinder.ui.screens

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.model.Job
import com.example.jobfinder.ui.JobFinderViewModel
import com.example.jobfinder.ui.viewmodel.JobFinderUiState
import kotlinx.coroutines.launch

//  Navigation destinations

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Notifications : Screen("notifications")

    data object JobDetail : Screen("jobDetail/{jobId}") {
        fun createRoute(jobId: String) = "jobDetail/$jobId"
    }

    data object ResumeCamera : Screen("resumeCamera/{jobId}") {
        fun createRoute(jobId: String) = "resumeCamera/$jobId"
    }

    data object FinalApply : Screen("finalApply/{jobId}/{imageUri}") {
        fun createRoute(jobId: String, imageUri: String) =
            "finalApply/$jobId/$imageUri"
    }
}

//  Root composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobFinderApp(
    viewModel: JobFinderViewModel = viewModel(factory = JobFinderViewModel.Factory)
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (!isLandscape) {
        // ---------------------------------
        // PORTRAIT: Top bar + Bottom bar
        // ---------------------------------
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
                val navBackStackEntry by
                navController.currentBackStackEntryAsState()
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
                                        popUpTo(Screen.Home.route) {
                                            saveState = true
                                        }
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

                                    Screen.FinalApply -> TODO()
                                    Screen.JobDetail -> TODO()
                                    Screen.ResumeCamera -> TODO()
                                }
                            },
                            label = {
                                Text(
                                    when (screen) {
                                        Screen.Home -> "Home"
                                        Screen.Favorites -> "Favorites"
                                        Screen.Notifications -> "Notifications"
                                        Screen.FinalApply -> TODO()
                                        Screen.JobDetail -> TODO()
                                        Screen.ResumeCamera -> TODO()
                                    }
                                )
                            },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme
                                    .onPrimaryContainer.copy(alpha = 0.7f),
                                unselectedTextColor = MaterialTheme.colorScheme
                                    .onPrimaryContainer.copy(alpha = 0.7f),
                                indicatorColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            JobFinderNavHost(
                navController = navController,
                uiState = uiState,
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    } else {
        // ---------------------------------
        // LANDSCAPE: Modal drawer + Top bar
        // ---------------------------------
        val drawerState =
            rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text(
                        text = "Job Finder",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    val destinations = listOf(
                        Screen.Home to "Home",
                        Screen.Favorites to "Favorites",
                        Screen.Notifications to "Notifications"
                    )

                    val navBackStackEntry by
                    navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    destinations.forEach { (screen, label) ->
                        NavigationDrawerItem(
                            label = { Text(label) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.padding(
                                NavigationDrawerItemDefaults.ItemPadding
                            )
                        )
                    }
                }
            }
        ) {
            Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = { /* optional title in landscape */ },
//                        navigationIcon = {
//                            IconButton(
//                                onClick = {
//                                    scope.launch { drawerState.open() }
//                                }
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Menu,
//                                    contentDescription = "Menu"
//                                )
//
//                            }
//
//                        }
//                    )
//                }
                // no bottomBar here
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
                JobFinderNavHost(
                    navController = navController,
                    uiState = uiState,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

// ----- Shared NavHost for all layouts -----

@Composable
private fun JobFinderNavHost(
    navController: NavHostController,
    uiState: JobFinderUiState,
    viewModel: JobFinderViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // HOME
        composable(Screen.Home.route) {
            HomeScreen(
                searchText = uiState.searchText,
                jobs = uiState.jobs,
                isSearching = uiState.isSearching,
                onSearchTextChange = { viewModel.onSearchTextChange(it) },
                onJobSelected = { job ->
                    navController.navigate(
                        Screen.JobDetail.createRoute(job.id)
                    )
                }
            )
        }

        // FAVORITES
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

        // NOTIFICATIONS
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
                    navController.navigate(
                        Screen.ResumeCamera.createRoute(job.id)
                    )
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
                    val encodedUri = Uri.encode(uri.toString())
                    navController.navigate(
                        Screen.FinalApply.createRoute(jobId, encodedUri)
                    )
                }
            )
        }

        // FINAL APPLY
        composable(
            route = Screen.FinalApply.route,
            arguments = listOf(
                navArgument("jobId") { type = NavType.StringType },
                navArgument("imageUri") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")!!
            val imageUriStr =
                backStackEntry.arguments?.getString("imageUri")!!
            val imageUri = Uri.parse(Uri.decode(imageUriStr))

            val job: Job? = uiState.jobs.firstOrNull { it.id == jobId }

            FinalApplyScreen(
                job = job,
                imageUri = imageUri,
                onCancel = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRetake = {
                    navController.popBackStack()
                },
                onApply = {
                    // 1. Mark applied in DB + UI
                    viewModel.  markJobAsApplied(jobId)

                    // 2. Navigate home
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}