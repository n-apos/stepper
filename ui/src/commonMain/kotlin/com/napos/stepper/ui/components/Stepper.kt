package com.napos.stepper.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.napos.stepper.core.Roadmap
import com.napos.stepper.ui.screen.MilestoneScreenProvider

@Composable
public fun Stepper(
    roadmap: Roadmap,
    provider: MilestoneScreenProvider,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    properties: StepProperties = StepProperties.Default,
    colors: StepColors = StepColors.Default.defaultColors(),
    stepComponent: @Composable (StepState) -> Unit = { Step(it) },
    linkComponent: @Composable (StepState) -> Unit = { StepLink(it) },
    nextButton: @Composable (onClick: () -> Unit) -> Unit = { StepButton(text = "Next", onClick = it) },
    previousButton: @Composable (onClick: () -> Unit) -> Unit = { StepButton(text = "Previous", onClick = it) },
    submitButton: @Composable (onClick: () -> Unit) -> Unit = { StepButton(text = "Submit", onClick = it) },
) {
    val current by roadmap.current.collectAsState(null)

    CompositionLocalProvider(
        LocalStepProperties provides properties,
        LocalStepColors provides colors,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
                    .width(IntrinsicSize.Min)
                    .align(Alignment.Center),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 40.dp)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    val currentIndex = roadmap.currentIndex
                    roadmap.milestones.forEachIndexed { index, _ ->
                        val state = when {
                            index < currentIndex -> StepState.PASSED
                            index == currentIndex -> StepState.CURRENT
                            else -> StepState.COMING
                        }
                        if (index != 0) {
                            linkComponent(state)
                        }
                        stepComponent(state)
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(10.dp),
                ) {
                    current?.let { provider.provide(it).render() }
                }


                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
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
}
