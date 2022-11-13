package com.example.f1_app.presentation.viewmodels.history

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.common.Resource
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsSeasonUseCase
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.viewmodels.history.item_vm.ConstructorVM
import com.example.f1_app.presentation.viewmodels.history.item_vm.toRecyclerViewItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryTeamsViewModel @Inject constructor(
    private val constructorStandingsSeasonUseCase: ConstructorStandingsSeasonUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val events = MutableSharedFlow<HistoryViewModel.Event>()
    val uiEvents: SharedFlow<HistoryViewModel.Event> = events
    val data: ObservableField<List<RecyclerViewItem>> = ObservableField(emptyList())
    private var constructorStandingsList: List<ConstructorItem> = mutableListOf()
    val season: ObservableField<String> = ObservableField("")

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
            fetchConstructors()
        }.invokeOnCompletion {
            createRecyclerItems()
        }
    }

    init {
        season.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModelScope.launch {
                    fetchConstructors()
                }.invokeOnCompletion {
                    createRecyclerItems()
                }
            }
        })
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
                newList.add(it.toRecyclerViewItem())
            }
        }

        data.set(newList)
    }

    private suspend fun fetchConstructors() {
        when (val result = constructorStandingsSeasonUseCase.execute(season.get()?.takeLast(4)!!)) {
            is Resource.Success -> {
                result.data?.apply {
                    constructorStandingsList = this.map { constr ->
                        ConstructorItem(
                            id = constr.id,
                            name = constr.constructorName,
                            position = constr.position,
                            points = constr.points,
                            wins = constr.wins
                        )
                    }.distinctBy { it.name }
                }
            }
            is Resource.Error -> events.emit(HistoryViewModel.Event.FetchingErrorEvent)
            else -> {}
        }
    }
}