package com.example.f1_app.presentation.viewmodels.driverDetails

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class DriverDetailsResultsViewModel @Inject constructor(

) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<DriverDetailsViewModel.Event>()
    val uiEvents: SharedFlow<DriverDetailsViewModel.Event> = events
    var driverId: String = ""
}