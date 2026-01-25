package com.napos.stepper.compose.screen

import androidx.compose.runtime.Composable
import com.napos.stepper.core.Milestone

public abstract class MilestoneScreen<M : Milestone<*>, VM : MilestoneViewModel> {

    protected abstract val milestone: Milestone<*>
    protected abstract val viewModel: VM

    @Composable
    public abstract fun title(): String

    @Composable
    public abstract fun render()
}