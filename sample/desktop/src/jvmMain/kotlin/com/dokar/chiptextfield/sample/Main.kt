package com.dokar.chiptextfield.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        title = "ChipTextField Sample",
        onCloseRequest = ::exitApplication,
    ) {
        SampleScreen()
    }
}