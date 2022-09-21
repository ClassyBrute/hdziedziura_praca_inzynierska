package com.example.f1_app.presentation.viewmodels.home.item_vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.f1_app.presentation.homeRvItems.CarouselRaceItem
import com.example.f1_app.common.RecyclerViewItem

class CarouselRaceVM(val item: CarouselRaceItem) {
    val data: ObservableList<RecyclerViewItem> = ObservableArrayList<RecyclerViewItem>().apply {
//        addAll(item.raceItems.map { it.toRecyclerViewItem() })
    }
}