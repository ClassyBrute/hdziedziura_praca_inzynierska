package com.example.f1_app.presentation.viewmodels.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is teams Fragment"
    }
    val text: LiveData<String> = _text
}