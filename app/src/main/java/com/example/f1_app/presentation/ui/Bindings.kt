package com.example.f1_app.presentation.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.f1_app.R
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

@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: Int?) {
    if (!url?.equals(0)!!) {
        Glide
            .with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_f1)
            .into(view)
    }
}

@BindingAdapter("colorInt")
fun convertColor(view: AppCompatImageView, value: Int?) {
    if (value != null) {
        view.setBackgroundResource(value)
    }
}