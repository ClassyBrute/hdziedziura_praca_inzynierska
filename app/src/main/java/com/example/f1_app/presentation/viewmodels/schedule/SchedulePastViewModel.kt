package com.example.f1_app.presentation.viewmodels.schedule

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.next_race.NextRaceUseCase
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCase
import com.example.f1_app.presentation.homeRvItems.RaceItem
import com.example.f1_app.presentation.viewmodels.schedule.item_vm.RaceVM
import com.example.f1_app.presentation.viewmodels.schedule.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SchedulePastViewModel @Inject constructor(
    private val raceUseCase: NextRaceUseCase,
    private val resultsLatestUseCase: ResultsLatestUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<ScheduleViewModel.Event>()
    val uiEvents: SharedFlow<ScheduleViewModel.Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var raceList: List<RaceItem> = mutableListOf()
    private var round: String = ""

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchRound()
            fetchRaces()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    private fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

        raceList.forEach { item ->
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
        if (round.isEmpty()) {
            data.set(newList)
        } else {
            data.set(newList.take(round.toInt()))
        }
    }

    private suspend fun fetchRound() {
        when (val result = resultsLatestUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    round = this[0].round
                }
            }
            is Resource.Error -> events.emit(ScheduleViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

    private suspend fun fetchRaces() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        when (val result = raceUseCase.execute()) {
            is Resource.Success -> {
                result.data?.apply {
                    raceList = this.map {
                        RaceItem(
                            round = it.round,
                            country = it.name,
                            dateFrom = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(format.parse(it.dateFrom)!!),
                            dateTo = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(format.parse(it.dateTo)!!),
                            image = it.image,
                            raceName = it.raceName,
                            circuitName = it.circuitName,
                            map = it.map
                        )
                    }.distinctBy { it.round }
                }
            }
            is Resource.Error -> events.emit(ScheduleViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }
}