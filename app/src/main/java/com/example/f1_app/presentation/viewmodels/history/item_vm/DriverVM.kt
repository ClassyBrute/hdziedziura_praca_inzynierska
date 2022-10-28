package com.example.f1_app.presentation.viewmodels.history.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.history.HistoryViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class DriverVM(
    override val item: DriverItem
) : ItemVm<DriverItem>(), ItemWithEvent<HistoryViewModel.Event> {
    private val events: MutableSharedFlow<HistoryViewModel.Event> = MutableSharedFlow()

    fun onDriverClick() {
        itemsScope.launch {
            events.emit(HistoryViewModel.Event.DriverClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<HistoryViewModel.Event> {
        return events
    }
}

fun DriverVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_history_driver, this)