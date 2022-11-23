package com.example.f1_app.presentation.viewmodels.raceDetails

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlin.random.Random

class RaceDetailsViewModel @Inject constructor(

) : ViewModel(), DefaultLifecycleObserver{
    var raceName: String = ""
    var circuitName: String = ""
    var image: Int = 0
    var map: Int = 0
    var firstGp: String = Random.nextInt(1970, 2022).toString()
    var fastestLap: String = "1:${Random.nextInt(10, 59)}:${Random.nextInt(100, 1000)}"
}