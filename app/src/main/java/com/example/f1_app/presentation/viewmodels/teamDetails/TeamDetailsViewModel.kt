package com.example.f1_app.presentation.viewmodels.teamDetails

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.Resource
import com.example.f1_app.data.mocks.mockTeams
import com.example.f1_app.domain.model.Constructor
import com.example.f1_app.domain.use_case.constructor.ConstructorDetailsUseCase
import com.example.f1_app.presentation.homeRvItems.ConstructorResultsRaceItem
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamDetailsViewModel @Inject constructor(
    private val teamDetailsUseCase: ConstructorDetailsUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val adapter: ObservableField<ViewPagerAdapter> = ObservableField()
    val uiEvents: SharedFlow<Event> = events
    var teamId: String = ""
    var season: String = ""
    var teamDetails: ObservableField<Constructor> = ObservableField(Constructor())
    var color: ObservableInt = ObservableInt(0)

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchConstructor()
        }
    }

    fun emitInnerEvents(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    private suspend fun fetchConstructor() {
        when (val result = teamDetailsUseCase.execute(teamId)) {
            is Resource.Success -> {
                result.data?.apply {
                    val team = Constructor(
                        constructorId = this.constructorId,
                        name = this.name,
                        nationality = this.nationality
                    )
                    teamDetails.set(team)
                    mockTeams[team.name]?.let { color.set(it) }
                }
            }
            is Resource.Error -> events.emit(Event.FetchingErrorEvent)
            else -> {}
        }
    }

    sealed class Event {
        object FetchingErrorEvent : Event()
        object EmptyResults : Event()
        class RaceClickEvent(val item: ConstructorResultsRaceItem, val position: Int) : Event()
    }
}