package com.example.f1_app.presentation.viewmodels.home.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class ConstructorVM(
    override val item: ConstructorItem
) : ItemVm<ConstructorItem>(), ItemWithEvent<HomeViewModel.Event> {
    private val events: MutableSharedFlow<HomeViewModel.Event> = MutableSharedFlow()

    fun onConstructorClick() {
        itemsScope.launch {
            events.emit(HomeViewModel.Event.ConstructorClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<HomeViewModel.Event> {
        return events
    }
}

fun ConstructorVM.toRecyclerView() = RecyclerViewItem(R.layout.item_home_teams_standings, this)