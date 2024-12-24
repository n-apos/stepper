package com.napos.stepper.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.napos.stepper.core.Roadmap
import com.napos.stepper.ui.screen.MilestoneScreenProvider
import org.koin.compose.koinInject

@Composable
public fun Stepper(
    roadmap: Roadmap,
    properties: StepProperties = StepProperties.Default,
    colors: StepColors = StepColors.Default,
    passedStep: @Composable () -> Unit = { PassedStep() },
    currentStep: @Composable () -> Unit = { CurrentStep() },
    comingStep: @Composable () -> Unit = { ComingStep() },
    passedLink: @Composable () -> Unit = { PassedStepLink() },
    comingLink: @Composable () -> Unit = { ComingStepLink() },
    nextButton: @Composable (onClick: () -> Unit) -> Unit = { StepButton(onClick = it, text = "Next") },
    previousButton: @Composable (onClick: () -> Unit) -> Unit = { StepButton(onClick = it, text = "Previous") },
    submitButton: @Composable (onClick: () -> Unit) -> Unit = { StepButton(onClick = it, text = "Submit") },
    onSubmit: () -> Unit = {},
) {
    val current by roadmap.current.collectAsState(null)
    val provider: MilestoneScreenProvider = koinInject()

    CompositionLocalProvider(
        LocalStepperPropertiesProvider provides properties,
        LocalStepperColorsProvider provides colors,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                val currentIndex = roadmap.currentIndex
                roadmap.milestones
                    .forEachIndexed { index, _ ->
                        when {
                            index < currentIndex -> {
                                if (index != 0) {
                                    passedLink()
                                }
                                passedStep()
                            }

                            index == currentIndex -> {
                                if (index != 0) {
                                    passedLink()
                                }
                                currentStep()
                            }

                            index > currentIndex -> {
                                if (index != 0) {
                                    comingLink()
                                }
                                comingStep()
                            }
                        }
                    }
            }

            current?.let { provider.provide(it).render() }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                current?.previous?.let {
                    previousButton {
                        roadmap.previous()
                    }
                }
                current?.next?.let {
                    nextButton {
                        roadmap.next()
                    }
                } ?: run {
                    submitButton {
                        onSubmit()
                    }
                }
            }
        }
    }
}