package com.example.f1_app.presentation.viewmodels.driverDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.DriverStandings
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCase
import com.example.f1_app.presentation.homeRvItems.DriverResultsRaceItem
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DriverDetailsViewModel @Inject constructor(
    private val driverDetailsUseCase: DriverDetailsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val adapter: ObservableField<ViewPagerAdapter> = ObservableField()
    val uiEvents: SharedFlow<Event> = events
    var driverId: String = ""
    var driverDetails: ObservableField<DriverStandings> = ObservableField(DriverStandings())

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDriver()
        }
    }

    fun emitInnerEvents(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    private suspend fun fetchDriver() {
        when (val result = driverDetailsUseCase.execute(driverId)) {
            is Resource.Success -> {
                result.data?.apply {
                    val driver = DriverStandings(
                        this.driverId,
                        driverName = this.givenName,
                        driverSurname = this.familyName
                    )
                    driverDetails.set(driver)
                }
            }
            is Resource.Error -> events.emit(Event.FetchingErrorEvent)
            else -> {}
        }
    }

    sealed class Event {
        object FetchingErrorEvent : Event()
        object EmptyResults : Event()
        class RaceClickEvent(val item: DriverResultsRaceItem, val position: Int) : Event()
    }
}