package com.example.jobfinder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.data.NotificationDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class NotificationDashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = NotificationDatabase
        .getInstance(app)
        .notificationDao()

    val notifications = dao.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
}
