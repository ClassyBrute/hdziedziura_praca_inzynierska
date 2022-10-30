package com.example.f1_app.presentation.viewmodels.history

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.next_race.RacesSeasonUseCase
import com.example.f1_app.presentation.homeRvItems.RaceItem
import com.example.f1_app.presentation.viewmodels.history.item_vm.RaceVM
import com.example.f1_app.presentation.viewmodels.history.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Inject

class HistoryScheduleViewModel @Inject constructor(
    private val racesSeasonUseCase: RacesSeasonUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<HistoryViewModel.Event>()
    val uiEvents: SharedFlow<HistoryViewModel.Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var racesList: List<RaceItem> = mutableListOf()
    val season: ObservableField<String> = ObservableField("")

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchRaces()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    init {
        season.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModelScope.launch {
                    fetchRaces()
                }.invokeOnCompletion {
                    createRecyclerItems()
                }
            }
        })
    }

    private fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

        racesList.forEach { item ->
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

    private suspend fun fetchRaces() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        when (val result = racesSeasonUseCase.execute(season.get()?.takeLast(4)!!)) {
            is Resource.Success -> {
                result.data?.apply {
                    racesList = this.map {
                        RaceItem(
                            round = it.round,
                            country = it.name,
                            dateFrom = DateFormat.getDateInstance(DateFormat.MEDIUM).format(format.parse(it.dateTo)!!),
                            image = it.image,
                            raceName = it.raceName
                        )
                    }.distinctBy { it.round }
                }
            }
            is Resource.Error -> events.emit(HistoryViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }

}