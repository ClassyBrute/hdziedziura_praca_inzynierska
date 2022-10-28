package com.example.f1_app.presentation.viewmodels.teams.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.teams.TeamsViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class ConstructorVM(
    override val item: ConstructorItem
) : ItemVm<ConstructorItem>(), ItemWithEvent<TeamsViewModel.Event> {
    private val events: MutableSharedFlow<TeamsViewModel.Event> = MutableSharedFlow()

    fun onConstructorClick() {
        itemsScope.launch {
            events.emit(TeamsViewModel.Event.ConstructorClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<TeamsViewModel.Event> {
        return events
    }
}

fun ConstructorVM.toRecyclerView() = RecyclerViewItem(R.layout.item_teams_team, this)