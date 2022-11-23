package com.example.f1_app.presentation.viewmodels.teamDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.driver.DriverResultsSeasonUseCase
import com.example.f1_app.domain.use_case.standings.DriverStandingsSeasonUseCase
import com.example.f1_app.presentation.homeRvItems.ConstructorResultsRaceItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.homeRvItems.DriverResultsRaceItem
import com.example.f1_app.presentation.viewmodels.teamDetails.item_vm.RaceVM
import com.example.f1_app.presentation.viewmodels.teamDetails.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamDetailsResultsViewModel @Inject constructor(
    private val driverResultsSeasonUseCase: DriverResultsSeasonUseCase,
    private val driverStandingsSeasonUseCase: DriverStandingsSeasonUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<TeamDetailsViewModel.Event>()
    val uiEvents: SharedFlow<TeamDetailsViewModel.Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    var teamId: String = ""
    var teamName: String = ""
    private var rounds: Int = 0
    var driverIds: List<String> = listOf()
    val season: ObservableField<String> = ObservableField("2022")
    private var driversRaces: MutableList<DriverResultsRaceItem> = mutableListOf()
    private var races: MutableList<ConstructorResultsRaceItem> = mutableListOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDriverStandings()
            fetchDriversResults(driverIds)
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    fun createRecyclerItems() {
        races.clear()
        val newList = mutableListOf<RecyclerViewItem>()

        for (round in 1..rounds) {
            val driver1 = driversRaces.find { it.round == round.toString() }
            val driver2 = driversRaces.findLast { it.round == round.toString() }

            races.add(
                ConstructorResultsRaceItem(
                    round = round.toString(),
                    country = driver1?.country.toString(),
                    driver1 = driver1?.name.toString(),
                    position1 = driver1?.position.toString(),
                    points1 = driver1?.points.toString(),
                    driver2 = driver2?.name.toString(),
                    position2 = driver2?.position.toString(),
                    points2 = driver2?.points.toString(),
                    raceName = driver1?.raceName.toString(),
                    circuitName = driver1?.circuitName.toString(),
                    map = driver1?.map,
                    image = driver1?.image
                )
            )
        }

        races.forEach { item ->
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

    suspend fun fetchDriverStandings() {
        when (val result = driverStandingsSeasonUseCase.execute(season.get()!!)) {
            is Resource.Success -> {
                result.data?.apply {
                    val driverStandingsList = this.map {
                        DriverItem(
                            name = "${it.driverName} ${it.driverSurname.uppercase()}",
                            team = it.constructorName,
                            driverId = it.driverId
                        )
                    }
                    driverIds = driverStandingsList
                        .filter {
                            it.team == teamName
                        }.map {
                            it.driverId
                        }
                    if (driverIds.isEmpty()) events.emit(TeamDetailsViewModel.Event.EmptyResults)
                }
            }
            is Resource.Error -> events.emit(TeamDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    suspend fun fetchDriversResults(driverIds: List<String>) {
        driversRaces.clear()
        rounds = 0
        driverIds.forEach { id ->
            when (val result = driverResultsSeasonUseCase.execute(season.get()!!, id)) {
                is Resource.Success -> {
                    result.data?.apply {
                        driversRaces.addAll(this.map {
                            DriverResultsRaceItem(
                                it.round,
                                it.country,
                                it.constructor,
                                it.position,
                                it.points,
                                it.name,
                                raceName = it.raceName,
                                circuitName = it.circuitName,
                                image = it.image,
                                map = it.map
                            )
                        })
                        driversRaces.forEach {
                            if (it.round.toInt() > rounds) rounds = it.round.toInt()
                        }
                    }
                }
                is Resource.Error -> events.emit(TeamDetailsViewModel.Event.FetchingErrorEvent)
                else -> {}
            }
        }
    }
}