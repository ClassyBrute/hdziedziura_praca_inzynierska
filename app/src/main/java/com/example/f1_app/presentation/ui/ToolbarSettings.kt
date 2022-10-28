package com.example.f1_app.presentation.ui

interface ToolbarSettings {
    val showToolbar: Boolean
        get() = false
    val showBottomNavBar: Boolean
        get() = false
    val toolbarTitle: String
        get() = ""
    val showSeason: Boolean
        get() = false
}