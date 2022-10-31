package com.example.f1_app.presentation.viewmodels.history

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.standings.DriverStandingsSeasonUseCase
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.history.item_vm.DriverVM
import com.example.f1_app.presentation.viewmodels.history.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryDriversViewModel @Inject constructor(
    private val driverStandingsSeasonUseCase: DriverStandingsSeasonUseCase,
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<HistoryViewModel.Event>()
    val uiEvents: SharedFlow<HistoryViewModel.Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var driverStandingsList: List<DriverItem> = mutableListOf()
    val season: ObservableField<String> = ObservableField("")

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDrivers()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    init {
        season.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModelScope.launch {
                    fetchDrivers()
                }.invokeOnCompletion {
                    createRecyclerItems()
                }
            }
        })
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

    private suspend fun fetchDrivers() {
        when (val result = driverStandingsSeasonUseCase.execute(season.get()?.takeLast(4)!!)) {
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
            is Resource.Error -> events.emit(HistoryViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

}