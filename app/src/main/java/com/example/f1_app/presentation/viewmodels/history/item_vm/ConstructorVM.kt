package com.example.f1_app.presentation.viewmodels.history.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.history.HistoryViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class ConstructorVM(
    override val item: ConstructorItem
) : ItemVm<ConstructorItem>(), ItemWithEvent<HistoryViewModel.Event> {
    private val events: MutableSharedFlow<HistoryViewModel.Event> = MutableSharedFlow()

    fun onConstructorClick() {
        itemsScope.launch {
            events.emit(HistoryViewModel.Event.ConstructorClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<HistoryViewModel.Event> {
        return events
    }
}

fun ConstructorVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_history_team, this)