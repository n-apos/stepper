package com.napos.stepper.example.steps.first

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.napos.stepper.example.Res
import com.napos.stepper.example.stepper_first_screen_title
import com.napos.stepper.ui.screen.MilestoneScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FirstScreen(
    override val milestone: FirstMilestone,
) : MilestoneScreen<FirstMilestone, FirstViewModel>(), KoinComponent {

    override val viewModel: FirstViewModel by inject()

    @Composable
    override fun title(): String = stringResource(Res.string.stepper_first_screen_title)

    @Composable
    override fun render() {
        var value by remember { mutableStateOf(milestone.data?.value ?: "") }
        milestone.data = FirstData(
            value = value,
        )

        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("First value") }
        )
    }
}
