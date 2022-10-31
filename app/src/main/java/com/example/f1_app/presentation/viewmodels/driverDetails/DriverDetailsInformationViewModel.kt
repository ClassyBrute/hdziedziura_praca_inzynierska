package com.example.f1_app.presentation.viewmodels.driverDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.Driver
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCase
import com.example.f1_app.domain.use_case.driver.DriverRacesAllTimeUseCase
import com.example.f1_app.domain.use_case.driver.DriverStatsAllTimeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DriverDetailsInformationViewModel @Inject constructor(
    private val driverStatsAllTimeUseCase: DriverStatsAllTimeUseCase,
    private val driverRacesAllTimeUseCase: DriverRacesAllTimeUseCase,
    private val driverDetailsUseCase: DriverDetailsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<DriverDetailsViewModel.Event>()
    val uiEvents: SharedFlow<DriverDetailsViewModel.Event> = events
    var driverId: String = ""
    var driverStats: ObservableField<Map<String, String>> = ObservableField(mapOf())
    var driverRaces: ObservableField<String> = ObservableField("")
    var driverDetails: ObservableField<Driver> = ObservableField(Driver())

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDriverStats()
            fetchDriverRaces()
            fetchDriverDetails()
        }
    }

    private suspend fun fetchDriverStats() {
        when (val result = driverStatsAllTimeUseCase.execute(driverId)) {
            is Resource.Success -> {
                result.data?.apply {
                    driverStats.set(this)
                }
            }
            is Resource.Error -> events.emit(DriverDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchDriverRaces() {
        when (val result = driverRacesAllTimeUseCase.execute(driverId)) {
            is Resource.Success -> {
                result.data?.apply {
                    driverRaces.set(this)
                }
            }
            is Resource.Error -> events.emit(DriverDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchDriverDetails() {
        when (val result = driverDetailsUseCase.execute(driverId)) {
            is Resource.Success -> {
                result.data?.apply {
                    driverDetails.set(this)
                }
            }
            is Resource.Error -> events.emit(DriverDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }
}