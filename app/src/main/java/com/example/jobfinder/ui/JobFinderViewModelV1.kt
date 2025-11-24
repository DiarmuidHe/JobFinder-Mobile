//package com.example.jobfinder.ui
//
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.jobfinder.JobFinderApplication
//import com.example.jobfinder.R
//import com.example.jobfinder.data.UserPreferencesRepository
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//
///*
// * View model of Jobfinder Release components
// */
//class JobFinderViewModel(
//    private val userPreferencesRepository: UserPreferencesRepository
//) : ViewModel() {
//
//    val uiState: StateFlow<JobFinderUiState> =
//        userPreferencesRepository.isLinearLayout.map { isLinearLayout ->
//            JobFinderUiState(isLinearLayout)
//        }.stateIn(
//            scope = viewModelScope,
//            // Flow is set to emits value for when app is on the foreground
//            // 5 seconds stop delay is added to ensure it flows continuously
//            // for cases such as configuration change
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = runBlocking {
//                JobFinderUiState(
//                    isLinearLayout = userPreferencesRepository.isLinearLayout.first()
//                )
//            }
//        )
//
//    /*
//     * [selectLayout] change the layout and icons accordingly and
//     * save the selection in DataStore through [userPreferencesRepository]
//     */
//    fun selectLayout(isLinearLayout: Boolean) {
//        viewModelScope.launch {
//            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
//        }
//    }
//
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as JobFinderApplication)
//                JobFinderViewModel(application.userPreferencesRepository)
//            }
//        }
//    }
//}
//
///*
// * Data class containing various UI States for JobFinder Release screens
// */
//data class JobFinderUiState(
//    val isLinearLayout: Boolean = true,
//    val toggleContentDescription: Int =
//        if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
//    val toggleIcon: Int =
//        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
//)
