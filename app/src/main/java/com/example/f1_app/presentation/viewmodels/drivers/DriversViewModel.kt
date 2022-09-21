package com.example.f1_app.presentation.viewmodels.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DriversViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is drivers Fragment"
    }
    val text: LiveData<String> = _text
}