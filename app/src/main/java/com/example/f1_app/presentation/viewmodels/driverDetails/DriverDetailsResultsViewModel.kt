package com.example.f1_app.presentation.viewmodels.driverDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCase
import com.example.f1_app.domain.use_case.driver.DriverResultsSeasonUseCase
import com.example.f1_app.presentation.homeRvItems.DriverResultsRaceItem
import com.example.f1_app.presentation.viewmodels.driverDetails.item_vm.RaceVM
import com.example.f1_app.presentation.viewmodels.driverDetails.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DriverDetailsResultsViewModel @Inject constructor(
    private val driverResultsSeasonUseCase: DriverResultsSeasonUseCase,
    private val driverDetailsUseCase: DriverDetailsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<DriverDetailsViewModel.Event>()
    val uiEvents: SharedFlow<DriverDetailsViewModel.Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    var driverId: String = ""
    val season: ObservableField<String> = ObservableField("2022")
    private var driverRaces: List<DriverResultsRaceItem> = listOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDriverResults()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

        driverRaces.forEach { item ->
            RaceVM(
                item
            ).let {
                viewModelScope.launch {
                    it.events().collectLatest { raceEvent ->
                        events.emit(raceEvent)
                    }
                }
                newList.add(it.toRecyclerViewItem())
            }
        }

        data.set(newList)
    }

    suspend fun fetchDriverResults() {
        when (val result = driverResultsSeasonUseCase.execute(season.get()!!, driverId)) {
            is Resource.Success -> {
                result.data?.apply {
                    driverRaces = this.map {
                        DriverResultsRaceItem(
                            it.round,
                            it.country,
                            it.constructor,
                            it.position,
                            it.points
                        )
                    }
                    if (driverRaces.isEmpty()) events.emit(DriverDetailsViewModel.Event.EmptyResults)
                }
            }
            is Resource.Error -> events.emit(DriverDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }
}