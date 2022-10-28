package com.example.f1_app.presentation.viewmodels.teams

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsUseCase
import com.example.f1_app.domain.use_case.standings.DriverStandingsUseCase
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.teams.item_vm.ConstructorVM
import com.example.f1_app.presentation.viewmodels.teams.item_vm.toRecyclerView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamsViewModel @Inject constructor(
    private val constructorStandingsUseCase: ConstructorStandingsUseCase,
    private val driverStandingsUseCase: DriverStandingsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val uiEvents: SharedFlow<Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var constructorStandingsList: List<ConstructorItem> = mutableListOf()
    private var driverStandingsList: List<DriverItem> = mutableListOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchDriverStandings()
            fetchConstructorStandings()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    private fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

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

        data.set(newList)
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
        class ConstructorClickEvent(val item: ConstructorItem, val position: Int) : Event()
    }
}