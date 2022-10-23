package com.example.f1_app.presentation.viewmodels.schedule.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.RaceItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.schedule.ScheduleViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class RaceVM(
    override val item: RaceItem
) : ItemVm<RaceItem>(), ItemWithEvent<ScheduleViewModel.Event> {
    private val events: MutableSharedFlow<ScheduleViewModel.Event> = MutableSharedFlow()

    fun onRaceClick() {
        itemsScope.launch {
            events.emit(ScheduleViewModel.Event.RaceClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<ScheduleViewModel.Event> {
        return events
    }
}

fun RaceVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_schedule_race, this)