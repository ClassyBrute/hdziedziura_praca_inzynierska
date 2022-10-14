package com.example.f1_app.presentation.viewmodels.home.item_vm

import com.example.f1_app.R
import com.example.f1_app.common.RecyclerViewItem
import com.example.f1_app.presentation.homeRvItems.TextItem
import com.example.f1_app.presentation.viewmodels.ItemVm

data class TextVM (
    override val item: TextItem
) : ItemVm<TextItem>() {}

fun TextVM.toRecyclerViewItem() = RecyclerViewItem(R.layout.item_home_text, this)