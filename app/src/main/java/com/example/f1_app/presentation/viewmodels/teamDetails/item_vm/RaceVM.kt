package com.example.f1_app.presentation.viewmodels.teamDetails.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.ConstructorItem
import com.example.f1_app.presentation.homeRvItems.ConstructorResultsRaceItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class RaceVM(
    override val item: ConstructorResultsRaceItem
) : ItemVm<ConstructorResultsRaceItem>(), ItemWithEvent<TeamDetailsViewModel.Event> {
    private val events: MutableSharedFlow<TeamDetailsViewModel.Event> = MutableSharedFlow()

    fun onRaceClick() {
        itemsScope.launch {
            events.emit(TeamDetailsViewModel.Event.RaceClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<TeamDetailsViewModel.Event> {
        return events
    }
}

fun RaceVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_team_details_race, this)