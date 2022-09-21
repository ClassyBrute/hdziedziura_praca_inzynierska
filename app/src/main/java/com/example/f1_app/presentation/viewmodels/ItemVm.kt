package com.example.f1_app.presentation.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

const val NO_POSITION = -1

abstract class ItemVm<T> {
    abstract val item: T
    val itemsScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    var position : Int = NO_POSITION

    fun clearScope() {
        itemsScope.coroutineContext.cancelChildren()
    }
}