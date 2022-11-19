package com.example.f1_app.presentation.viewmodels.teamDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.Constructor
import com.example.f1_app.domain.model.ConstructorRace
import com.example.f1_app.domain.use_case.constructor.ConstructorDetailsUseCase
import com.example.f1_app.domain.use_case.constructor.ConstructorFirstRaceUseCase
import com.example.f1_app.domain.use_case.constructor.ConstructorStatsUseCase
import com.example.f1_app.domain.use_case.standings.DriverStandingsSeasonUseCase
import com.example.f1_app.presentation.homeRvItems.DriverItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamDetailsInformationViewModel @Inject constructor(
    private val constructorDetailsUseCase: ConstructorDetailsUseCase,
    private val constructorStatsUseCase: ConstructorStatsUseCase,
    private val constructorFirstRaceUseCase: ConstructorFirstRaceUseCase,
    private val driverStandingsSeasonUseCase: DriverStandingsSeasonUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<TeamDetailsViewModel.Event>()
    val uiEvents: SharedFlow<TeamDetailsViewModel.Event> = events
    var teamId: String = ""
    var season: String = ""
    var teamDetails: ObservableField<Constructor> = ObservableField(Constructor())
    var teamStats: ObservableField<Map<String, String>> = ObservableField(mapOf())
    var teamRace: ObservableField<ConstructorRace> = ObservableField(ConstructorRace())
    var driver1: ObservableField<String> = ObservableField("")
    var driver2: ObservableField<String> = ObservableField("")
    private var driverStandingsList: List<DriverItem> = mutableListOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchTeamDetails()
            fetchTeamStats()
            fetchTeamFirstRace()
            fetchDriverStandings()
        }
    }

    private suspend fun fetchDriverStandings() {
        when (val result = driverStandingsSeasonUseCase.execute(season)) {
            is Resource.Success -> {
                result.data?.apply {
                    driverStandingsList = this.map {
                        DriverItem(
                            name = "${it.driverName} ${it.driverSurname.uppercase()}",
                            team = it.constructorName,
                            driverId = it.driverId
                        )
                    }
                    driver1.set(driverStandingsList.find {
                        it.team == teamDetails.get()?.name
                    }?.name)
                    driver2.set(driverStandingsList.findLast {
                        it.team == teamDetails.get()?.name
                    }?.name)
                }
            }
            is Resource.Error -> events.emit(TeamDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchTeamDetails() {
        when (val result = constructorDetailsUseCase.execute(teamId)) {
            is Resource.Success -> {
                result.data?.apply {
                    teamDetails.set(this)
                }
            }
            is Resource.Error -> events.emit(TeamDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchTeamStats() {
        when (val result = constructorStatsUseCase.execute(teamId)) {
            is Resource.Success -> {
                result.data?.apply {
                    teamStats.set(this)
                }
            }
            is Resource.Error -> events.emit(TeamDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchTeamFirstRace() {
        when (val result = constructorFirstRaceUseCase.execute(teamId)) {
            is Resource.Success -> {
                result.data?.apply {
                    teamRace.set(this)
                }
            }
            is Resource.Error -> events.emit(TeamDetailsViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }
}