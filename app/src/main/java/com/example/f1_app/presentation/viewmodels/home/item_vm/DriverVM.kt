package com.example.f1_app.presentation.viewmodels.home.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class DriverVM(
    override val item: DriverItem
) : ItemVm<DriverItem>(), ItemWithEvent<HomeViewModel.Event> {
    private val events: MutableSharedFlow<HomeViewModel.Event> = MutableSharedFlow()

    fun onDriverClick() {
        itemsScope.launch {
            events.emit(HomeViewModel.Event.DriverClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<HomeViewModel.Event> {
        return events
    }
}

fun DriverVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_home_latest, this)