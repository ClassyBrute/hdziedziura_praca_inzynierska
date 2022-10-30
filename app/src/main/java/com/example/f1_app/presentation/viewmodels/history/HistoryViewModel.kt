package com.example.f1_app.presentation.viewmodels.history

import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.homeRvItems.RaceItem
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(

) : ViewModel(), DefaultLifecycleObserver {
    private val events = MutableSharedFlow<Event>()
    val adapter: ObservableField<ViewPagerAdapter> = ObservableField()
    val uiEvents: SharedFlow<Event> = events
    val season: ObservableField<String> = ObservableField("2022")

    fun emitInnerEvents(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    sealed class Event {
        object FetchingErrorEvent : Event()
        class DriverClickEvent(val item: DriverItem, val position: Int) : Event()
        class ConstructorClickEvent(val item: ConstructorItem, val position: Int) : Event()
        class RaceClickEvent(val item: RaceItem, val position: Int) : Event()
    }
}