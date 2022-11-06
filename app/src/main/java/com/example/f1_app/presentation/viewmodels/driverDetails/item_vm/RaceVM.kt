package com.example.f1_app.presentation.viewmodels.driverDetails.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.DriverResultsRaceItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


data class RaceVM(
    override val item: DriverResultsRaceItem
) : ItemVm<DriverResultsRaceItem>(), ItemWithEvent<DriverDetailsViewModel.Event> {
    private val events: MutableSharedFlow<DriverDetailsViewModel.Event> = MutableSharedFlow()

    fun onRaceClick() {
        itemsScope.launch {
            events.emit(DriverDetailsViewModel.Event.RaceClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<DriverDetailsViewModel.Event> {
        return events
    }
}

fun RaceVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_driver_details_race, this)