package com.napos.stepper.compose.screen

import com.napos.stepper.core.Milestone

public fun interface MilestoneScreenProvider {


    public fun provide(milestone: Milestone<*>): MilestoneScreen<*, *>

    public companion object {

        public inline operator fun invoke(crossinline block: (milestone: Milestone<*>) -> MilestoneScreen<*, *>): MilestoneScreenProvider =
            MilestoneScreenProvider { block(it) }

    }
}