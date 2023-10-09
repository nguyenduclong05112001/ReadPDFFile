package com.longhrk.app.ui

sealed class NavTarget(val route: String) {

    object Splash : NavTarget("splash")
    object Home : NavTarget("home")
    object PDFOnLine : NavTarget("pdf_online")
    object PDFOffLine : NavTarget("pdf_offline")
    object Setting : NavTarget("setting")

    override fun toString(): String {
        return route
    }
}