package com.example.f1_app.presentation.viewmodels.home.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.ShowItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class ShowAllVM (
    override val item: ShowItem
) : ItemVm<ShowItem>(), ItemWithEvent<HomeViewModel.Event> {
    private val events: MutableSharedFlow<HomeViewModel.Event> = MutableSharedFlow()

    fun onButtonClick() {
        itemsScope.launch {
            events.emit(HomeViewModel.Event.ShowDriversEvent)
        }
    }

    override fun events(): SharedFlow<HomeViewModel.Event> {
        return events
    }
}

fun ShowAllVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.button_show_all, this)