package com.napos.stepper.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.napos.stepper.core.Roadmap
import com.napos.stepper.compose.Res
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.compose.stepper_next_button
import com.napos.stepper.compose.stepper_previous_button
import com.napos.stepper.compose.stepper_submit_button
import org.jetbrains.compose.resources.stringResource

@Composable
public fun Stepper(
    roadmap: Roadmap,
    provider: MilestoneScreenProvider,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    properties: StepProperties = StepProperties.Default,
    colors: StepColors = StepColors.Default.defaultColors(),
    step: @Composable (StepState) -> Unit = { Step(it) },
    stepLink: @Composable (StepState) -> Unit = { StepLink(it) },
    nextButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_next_button),
            onClick = it
        )
    },
    previousButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_previous_button),
            onClick = it
        )
    },
    submitButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_submit_button),
            onClick = it
        )
    },
) {
    val current by roadmap.current.collectAsState(null)
    val screen = current?.let { provider.provide(it) }

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
                        .padding(10.dp)
                        .weight(0.1f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val currentIndex = roadmap.currentIndex
                    roadmap.milestones.forEachIndexed { index, _ ->
                        val state = when {
                            index < currentIndex -> StepState.Passed
                            index == currentIndex -> StepState.Current
                            else -> StepState.Coming
                        }
                        if (index != 0) {
                            stepLink(state)
                        }
                        step(state)
                    }
                }

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(10.dp)
                        .weight(0.8f),
                ) {
                    screen?.let {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ) {
                            Text(it.title())
                        }
                    }
                    screen?.render()
                }


                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .weight(0.1f),
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
