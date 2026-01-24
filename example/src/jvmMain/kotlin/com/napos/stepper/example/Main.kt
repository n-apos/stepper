package com.napos.stepper.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.napos.stepper.core.Roadmap
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import org.koin.compose.koinInject
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
        StepperApp()
    }
}