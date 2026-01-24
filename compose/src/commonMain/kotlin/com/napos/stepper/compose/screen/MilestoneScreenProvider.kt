package com.napos.stepper.compose.screen

import com.napos.stepper.core.Milestone

public abstract class MilestoneScreenProvider {


    public abstract fun provide(milestone: Milestone<*>): MilestoneScreen<*, *>
}