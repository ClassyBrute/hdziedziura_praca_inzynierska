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
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsUseCase
import com.example.f1_app.domain.use_case.standings.DriverStandingsUseCase
import com.example.f1_app.presentation.homeRvItems.*
import com.example.f1_app.presentation.viewmodels.home.item_vm.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val resultsLatestUseCase: ResultsLatestUseCase,
    private val nextRaceUseCase: NextRaceUseCase,
    private val driverStandingsUseCase: DriverStandingsUseCase,
    private val constructorStandingsUseCase: ConstructorStandingsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val uiEvents: SharedFlow<Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var latestList: List<DriverItem> = mutableListOf()
    private var nextList: List<RaceItem> = mutableListOf()
    private var driverStandingsList: List<DriverItem> = mutableListOf()
    private var constructorStandingsList: List<ConstructorItem> = mutableListOf()

    private var latestRaceName: String = ""
    private var round: String = ""

    private var homeJob: Job? = null
    private var startClicked: Boolean = false

    private var shortList = mutableListOf<RecyclerViewItem>()
    var newList = mutableListOf<RecyclerViewItem>()

    init {
        homeJob?.cancel()
        homeJob = viewModelScope.launch {
            launch {
                fetchResults()
                fetchNextRaces()
                fetchDriverStandings()
                fetchConstructorStandings()
            }.invokeOnCompletion {
                createRecyclerItems()
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            if (!startClicked) {
                events.emit(Event.NavigateToStart)
                startClicked = true
            }
        }
    }

    private fun createRecyclerItems() {
        val removeList = mutableListOf<RecyclerViewItem>()

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

        newList.add(
            TextVM(
                TextItem("Driver Standings")
            ).toRecyclerViewItem()
        )

        driverStandingsList.forEachIndexed { index, item ->
            DriverVM(
                item
            ).let {
                viewModelScope.launch {
                    it.events().collectLatest { driverEvent ->
                        events.emit(driverEvent)
                    }
                }
                if (index < 5) {
                    newList.add(it.toRecyclerViewVertical())
                } else {
                    removeList.add(it.toRecyclerViewVertical())
                    newList.add(it.toRecyclerViewVertical())
                }
            }
        }

        newList.add(
            ShowAllVM(
                ShowItem()
            ).let {
                viewModelScope.launch {
                    it.events().collectLatest { buttonEvent ->
                        events.emit(buttonEvent)
                    }
                }
                it.toRecyclerViewItem()
            }
        )

        newList.add(
            TextVM(
                TextItem("Constructor Standings")
            ).toRecyclerViewItem()
        )

        constructorStandingsList.forEach { item ->
            ConstructorVM(
                item
            ).let {
               viewModelScope.launch {
                    it.events().collectLatest { constructorEvent ->
                        events.emit(constructorEvent)
                    }
                }
                newList.add(it.toRecyclerView())
            }
        }

        shortList.addAll(newList.minus(removeList.toSet()))
        data.set(shortList)
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
                            image = it.image
                        )
                    }.distinctBy { it.name }
                }
                round = result.data?.get(0)?.round ?: ""
                latestRaceName = "Round $round - ${result.data?.get(0)?.raceName}"
            }
            is Resource.Error -> events.emit(Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchNextRaces() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        when (val result = nextRaceUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    nextList = this.map {
                        RaceItem(
                            round = it.round,
                            country = it.name,
                            dateFrom = DateFormat.getDateInstance(DateFormat.MEDIUM).format(format.parse(it.dateFrom)!!),
                            dateTo = DateFormat.getDateInstance(DateFormat.MEDIUM).format(format.parse(it.dateTo)!!),
                            image = it.image
                        )
                    }.distinctBy { it.round }
                }
            }
            is Resource.Error -> events.emit(Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchDriverStandings() {
        when (val result = driverStandingsUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    driverStandingsList = this.map {
                        DriverItem(
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

    private suspend fun fetchConstructorStandings() {
        when (val result = constructorStandingsUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    constructorStandingsList = this.map { constr ->
                        ConstructorItem(
                            name = constr.constructorName,
                            position = constr.position,
                            points = constr.points,
                            wins = constr.wins,
                            color = constr.color,
                            driver1 = driverStandingsList.find {
                                it.team == constr.constructorName
                            }?.surname.toString(),
                            driver2 = driverStandingsList.findLast {
                                it.team == constr.constructorName
                            }?.surname.toString()
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
        object NavigateToStart: Event()
        object ShowDriversEvent: Event()
        class ConstructorClickEvent(val item: ConstructorItem, val position: Int) : Event()
        class CarouselClickEvent(val item: CarouselDriverItem, val position: Int) : Event()
        class DriverClickEvent(val item: DriverItem, val position: Int) : Event()
        class RaceClickEvent(val item: RaceItem, val position: Int) : Event()
    }
}