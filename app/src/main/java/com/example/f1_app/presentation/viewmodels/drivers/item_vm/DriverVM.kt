package com.example.f1_app.presentation.viewmodels.drivers.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.DriverItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.drivers.DriversViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class DriverVM(
    override val item: DriverItem
) : ItemVm<DriverItem>(), ItemWithEvent<DriversViewModel.Event> {
    private val events: MutableSharedFlow<DriversViewModel.Event> = MutableSharedFlow()

    fun onDriverClick() {
        itemsScope.launch {
            events.emit(DriversViewModel.Event.DriverClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<DriversViewModel.Event> {
        return events
    }
}

fun DriverVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_drivers_driver, this)