package com.example.f1_app.presentation.viewmodels.home.item_vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.CarouselRaceItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CarouselRaceVM(
    override val item: CarouselRaceItem
) : ItemVm<CarouselRaceItem>(), ItemWithEvent<HomeViewModel.Event> {
    private val events = MutableSharedFlow<HomeViewModel.Event>()
    val data: ObservableList<RecyclerViewItem> = ObservableArrayList<RecyclerViewItem>().apply {
        addAll(
            item.raceItems.map { raceItem ->
                RaceVM(raceItem)
                    .let {
                        itemsScope.launch {
                            it.events().collectLatest { raceEvent ->
                                events.emit(raceEvent)
                            }
                        }
                        it.toRecyclerViewItem()
                    }
            }
        )
    }

    override fun events(): SharedFlow<HomeViewModel.Event> {
        return events
    }
}

fun CarouselRaceVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_home_category_next, this)