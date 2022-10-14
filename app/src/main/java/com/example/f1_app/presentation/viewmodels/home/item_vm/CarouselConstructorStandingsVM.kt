package com.example.f1_app.presentation.viewmodels.home.item_vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.f1_app.R
import com.example.f1_app.common.ItemWithEvent
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.CarouselConstructorItem
import com.example.f1_app.presentation.viewmodels.ItemVm
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CarouselConstructorStandingsVM (
    override val item: CarouselConstructorItem
) : ItemVm<CarouselConstructorItem>(), ItemWithEvent<HomeViewModel.Event> {
    private val events = MutableSharedFlow<HomeViewModel.Event>()
    val data: ObservableList<RecyclerViewItem> = ObservableArrayList<RecyclerViewItem>().apply {
        addAll(
            item.constructorItems.map { constructorItem ->
                ConstructorVM(constructorItem)
                    .let {
                        itemsScope.launch {
                            it.events().collectLatest { constructorEvent ->
                                events.emit(constructorEvent)
                            }
                        }
                        it.toRecyclerView()
                    }
            }
        )
    }

    override fun events(): SharedFlow<HomeViewModel.Event> {
        return events
    }
}

fun CarouselConstructorStandingsVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_home_category_team_standings, this)