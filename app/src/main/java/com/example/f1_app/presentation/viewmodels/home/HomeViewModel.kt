package com.example.f1_app.presentation.viewmodels.home

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.next_race.NextRaceUseCase
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCase
import com.example.f1_app.presentation.homeRvItems.CarouselDriverItem
import com.example.f1_app.presentation.homeRvItems.CarouselRaceItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.homeRvItems.RaceItem
import com.example.f1_app.presentation.viewmodels.home.item_vm.CarouselDriverVM
import com.example.f1_app.presentation.viewmodels.home.item_vm.CarouselRaceVM
import com.example.f1_app.presentation.viewmodels.home.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val resultsLatestUseCase: ResultsLatestUseCase,
    private val nextRaceUseCase: NextRaceUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val uiEvents: SharedFlow<Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    var latestList: List<DriverItem> = mutableListOf()
    private var latestRaceName: String = ""
    private var round: String = ""
    var nextList: List<RaceItem> = mutableListOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (latestList.isEmpty() && nextList.isEmpty()) {
            viewModelScope.launch {
                events.emit(Event.LoadingEvent)
                fetchResults()
                fetchNextRaces()
            }.invokeOnCompletion {
                viewModelScope.launch {
                    events.emit(Event.FetchingDoneEvent)
                }
            }
        }
        else {
            viewModelScope.launch {
                events.emit(Event.FetchingDoneEvent)
            }
        }
    }

    fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

        newList.add(
            CarouselDriverVM(
                CarouselDriverItem(
                    latestRaceName,
                    latestList
                )
            ).let {
                viewModelScope.launch {
                    it.events().collectLatest { event ->
                        events.emit(event)
                    }
                }
                it.toRecyclerViewItem()
            }
        )

        newList.add(
            CarouselRaceVM(
                CarouselRaceItem(
                    if (round.isEmpty()) {
                        nextList
                    } else nextList.drop(round.toInt())
                )
            ).let {
                viewModelScope.launch {
                    it.events().collectLatest { event ->
                        events.emit(event)
                    }
                }
                it.toRecyclerViewItem()
            }
        )

        data.set(newList)
    }

    private suspend fun fetchResults() {
        when (val result = resultsLatestUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    latestList = this.map {
                        DriverItem(
                            name = "${it.driverName}\n${it.driverSurname}",
                            position = it.position,
                            team = it.constructor,
                        )
                    }.distinctBy { it.name }
                }
                latestRaceName = result.data?.get(0)?.raceName ?: ""
                round = result.data?.get(0)?.round ?: ""
            }
            is Resource.Error -> {
                events.emit(Event.FetchingErrorEvent)
                result.data?.apply {
                    latestList = this.map {
                        DriverItem(
                            it.driverName,
                            it.position,
                            it.constructor
                        )
                    }
                }
            }
            else -> {

            }
        }
    }

    private suspend fun fetchNextRaces() {
        when (val result = nextRaceUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    nextList = this.map {
                        RaceItem(
                            round = it.round,
                            country = it.name,
                            dateFrom = it.dateFrom,
                            dateTo = it.dateTo
                        )
                    }.distinctBy { it.round }
                }
            }
            is Resource.Error -> {
                events.emit(Event.FetchingErrorEvent)
                result.data?.apply {
                    nextList = this.map {
                        RaceItem(
                            it.round,
                            it.name,
                            it.dateFrom,
                            it.dateTo
                        )
                    }
                }
            }
            else -> {

            }
        }
    }

    sealed class Event {
        object FetchingErrorEvent : Event()
        object FetchingDoneEvent : Event()
        object LoadingEvent : Event()
        class CarouselClickEvent(val item: CarouselDriverItem, val position: Int) : Event()
        class DriverClickEvent(val item: DriverItem, val position: Int) : Event()
        class RaceClickEvent(val item: RaceItem, val position: Int) : Event()
    }
}