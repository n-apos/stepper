package com.napos.stepper.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.stopKoin

fun main(): Unit = application {

    initializeKoin()

    Window(
        onCloseRequest = {
            stopKoin()
            exitApplication()
        },
        title = "Stepper",
    ) {
        StepperExample()
    }
}