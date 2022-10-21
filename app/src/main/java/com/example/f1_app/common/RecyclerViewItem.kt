package com.example.f1_app.common

import com.example.f1_app.presentation.viewmodels.ItemVm

data class RecyclerViewItem(val layoutId: Int, val itemViewModel: ItemVm<*>) {
    val viewType = getViewTypeId()

    private fun getViewTypeId(): Int {
        var result = 31
        result = 31 * result + layoutId
        return result
    }

}