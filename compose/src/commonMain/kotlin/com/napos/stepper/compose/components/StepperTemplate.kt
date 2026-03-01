package com.napos.stepper.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.napos.stepper.compose.Res
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.compose.stepper_edit_button
import com.napos.stepper.compose.stepper_start_button
import com.napos.stepper.core.Roadmap
import org.jetbrains.compose.resources.stringResource

/**
 * A template composable for creating different types of stepper screens, such as previews or step-by-step flows.
 *
 * All parameters for this composable are inherited from the main [Stepper] composable.
 */
@Composable
public fun StepperTemplate(
    roadmap: Roadmap,
    provider: MilestoneScreenProvider,
    type: StepperContentType,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    properties: StepProperties = StepProperties.Default,
    colors: StepColors = StepColors.Default.defaultColors(),
    components: StepperComponents = StepperComponents(),
    content: @Composable () -> Unit,
) {
    val current by roadmap.current.collectAsState(null)
    val properties = LocalStepProperties.current

    CompositionLocalProvider(
        LocalStepProperties provides properties,
        LocalStepColors provides colors,
    ) {
        Box(
            modifier = Modifier.fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxHeight()
                    .width(IntrinsicSize.Min)
                    .align(Alignment.TopCenter),
            ) {
                if (type == StepperContentType.Step) {
                    Row(
                        modifier = Modifier
                            .width(properties.size * (roadmap.size * 2 - 1))
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
                                components.stepLink(state)
                            }
                            components.step(state)
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f),
                ) {
                    content()
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f),
                ) {

                    when (type) {
                        StepperContentType.Preview -> {
                            val text = if (roadmap.milestones.all { it.data == null }) {
                                stringResource(Res.string.stepper_start_button)
                            } else {
                                stringResource(Res.string.stepper_edit_button)
                            }
                            components.startButton(text) {
                                onStart()
                            }
                        }

                        StepperContentType.Step -> {
                            current?.previous?.let {
                                components.previousButton {
                                    onPrevious()
                                }
                            }
                            current?.next?.let {
                                components.nextButton {
                                    onNext()
                                }
                            } ?: run {
                                components.submitButton {
                                    onSubmit()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
