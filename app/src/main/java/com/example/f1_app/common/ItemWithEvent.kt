package com.example.f1_app.common

import kotlinx.coroutines.flow.SharedFlow

interface ItemWithEvent<T: Any> {
    fun events() : SharedFlow<T>
}