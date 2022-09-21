package com.example.f1_app.presentation.viewmodels.home

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCase
import com.example.f1_app.presentation.homeRvItems.CarouselDriverItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.home.item_vm.CarouselDriverVM
import com.example.f1_app.presentation.viewmodels.home.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val resultsLatestUseCase: ResultsLatestUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val uiEvents: SharedFlow<Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var latestList: MutableList<DriverItem> = mutableListOf()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchResults()
            latestList = latestList.distinct().toMutableList()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    private fun createRecyclerItems() {
        val newList = mutableListOf<RecyclerViewItem>()

        newList.add(
            CarouselDriverVM(
                CarouselDriverItem(
                    "testing title",
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

        data.set(newList)
    }

    private suspend fun fetchResults() {
        when (val result = resultsLatestUseCase.execute()) {
            is Resource.Success -> {
                result.data?.forEach {
                    latestList.add(
                        DriverItem(
                            name = it.driverName,
                            position = it.position,
                            team = it.constructor
                        )
                    )
                }
            }
            is Resource.Error -> {
                events.emit(Event.FetchingErrorEvent)
            }
            else -> {

            }
        }
    }

//    private suspend fun fetchDrivers

//    init {
//        data.add(
//            CarouselDriverItem(
//                "Round 5 - USA",
//                listOf(
//                    DriverItem("Charles Leclerc", "1", "Ferrari"),
//                    DriverItem("Charles Leclerc", "1", "Ferrari"),
//                    DriverItem("Charles Leclerc", "1", "Ferrari"),
//                    DriverItem("Charles Leclerc", "1", "Ferrari"),
//                    )
//                ).toRecyclerViewItem()
//            )
//        data.add(
//            CarouselRaceItem(
//                listOf(
//                    RaceItem("6", "Spain", "20-22 May 2022"),
//                    RaceItem("6", "Spain", "20-22 May 2022"),
//                    RaceItem("6", "Spain", "20-22 May 2022"),
//                    RaceItem("6", "Spain", "20-22 May 2022"),
//                )
//            ).toRecyclerViewItem()
//        )
//    }

    sealed class Event {
        object FetchingErrorEvent : Event()
        class CarouselClickEvent(val item: CarouselDriverItem, val position: Int) : Event()
        class DriverClickEvent(val item: DriverItem, val position: Int) : Event()
    }
}