package com.example.f1_app.presentation.viewmodels.drivers

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.standings.DriverStandingsUseCase
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.drivers.item_vm.DriverVM
import com.example.f1_app.presentation.viewmodels.drivers.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DriversViewModel @Inject constructor(
    private val driverStandingsUseCase: DriverStandingsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val uiEvents: SharedFlow<Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var driverStandingsList: List<DriverItem> = mutableListOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDriverStandings()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    private fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

        driverStandingsList.forEach { item ->
            DriverVM(
                item
            ).let {
                viewModelScope.launch {
                    it.events().collectLatest { driverEvent ->
                        events.emit(driverEvent)
                    }
                }

                newList.add(it.toRecyclerViewItem())
            }
        }

        data.set(newList)
    }

    private suspend fun fetchDriverStandings() {
        when (val result = driverStandingsUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    driverStandingsList = this.map {
                        DriverItem(
                            driverId = it.driverId,
                            name = "${it.driverName} ${it.driverSurname.uppercase()}",
                            surname = it.driverSurname.uppercase(),
                            team = it.constructorName,
                            points = it.points,
                            positionStandings = it.position,
                            wins = it.wins,
                            image = it.image
                        )
                    }.distinctBy { it.name }
                }
            }
            is Resource.Error -> events.emit(Event.FetchingErrorEvent)
            else -> {}
        }
    }


    sealed class Event {
        object FetchingErrorEvent : Event()
        class DriverClickEvent(val item: DriverItem, val position: Int) : Event()
    }
}
