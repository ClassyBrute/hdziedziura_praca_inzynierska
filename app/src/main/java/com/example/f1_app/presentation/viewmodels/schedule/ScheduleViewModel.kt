package com.example.f1_app.presentation.viewmodels.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(

) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is schedule Fragment"
    }
    val text: LiveData<String> = _text
}