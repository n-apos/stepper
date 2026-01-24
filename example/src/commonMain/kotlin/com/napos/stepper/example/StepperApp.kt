package com.napos.stepper.example

import androidx.compose.runtime.Composable
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.core.Roadmap
import org.koin.compose.koinInject

@Composable
fun StepperApp() {
    val roadmap: Roadmap = koinInject()
    val provider: MilestoneScreenProvider = koinInject()

    StepperExample(
        roadmap = roadmap,
        provider = provider,
    )
}