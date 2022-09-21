package com.example.f1_app.presentation.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.ui.adapter.GeneralAdapter

@BindingAdapter("rvItems")
fun bindRvItems(recyclerView: RecyclerView, items: List<RecyclerViewItem>) {
    val adapter = recyclerView.adapter
    if (adapter == null) {
        recyclerView.adapter = GeneralAdapter().apply {
            update(items)
        }
    } else if (adapter is GeneralAdapter) {
        adapter.update(items)
    }
}