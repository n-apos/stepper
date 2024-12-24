package com.napos.stepper.ui.screen

import androidx.compose.runtime.Composable
import com.napos.stepper.core.Milestone

public abstract class MilestoneScreen<M : Milestone<*>, VM : MilestoneViewModel> {

    protected abstract val milestone: M
    protected abstract val viewModel: VM

    @Composable
    public abstract fun render()
}