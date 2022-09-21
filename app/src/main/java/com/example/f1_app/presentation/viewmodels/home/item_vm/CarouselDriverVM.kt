package com.example.f1_app.presentation.viewmodels.home.item_vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.CarouselDriverItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CarouselDriverVM(
    override val item: CarouselDriverItem
) : ItemVm<CarouselDriverItem>(), ItemWithEvent<HomeViewModel.Event>{
    private val events = MutableSharedFlow<HomeViewModel.Event>()
    val data: ObservableList<RecyclerViewItem> = ObservableArrayList<RecyclerViewItem>().apply {
        addAll(

            item.driverItems.map { driverItem ->
                DriverVM(driverItem)
                    .let {
                        itemsScope.launch {
                            it.events().collectLatest { driverEvent ->
                                events.emit(driverEvent)
                            }
                        }
                        it.toRecyclerViewItem()
                    }
            }
        )
    }

    fun onRaceClick() {
        itemsScope.launch {
            events.emit(HomeViewModel.Event.CarouselClickEvent(item, position))
        }
    }

    override fun events(): SharedFlow<HomeViewModel.Event> {
        return events
    }
}

fun CarouselDriverVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_home_category_latest, this)