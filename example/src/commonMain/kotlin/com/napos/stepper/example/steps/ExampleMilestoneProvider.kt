package com.napos.stepper.example.steps

import com.napos.stepper.core.Milestone
import com.napos.stepper.example.steps.first.FirstMilestone
import com.napos.stepper.example.steps.first.FirstScreen
import com.napos.stepper.example.steps.second.SecondMilestone
import com.napos.stepper.example.steps.second.SecondScreen
import com.napos.stepper.example.steps.third.ThirdMilestone
import com.napos.stepper.example.steps.third.ThirdScreen
import com.napos.stepper.ui.screen.MilestoneScreen
import com.napos.stepper.ui.screen.MilestoneScreenProvider

class ExampleMilestoneProvider: MilestoneScreenProvider() {

    override fun provide(milestone: Milestone<*>): MilestoneScreen<*, *> =
        when(milestone) {
            is FirstMilestone -> FirstScreen(milestone)
            is SecondMilestone -> SecondScreen(milestone)
            is ThirdMilestone -> ThirdScreen(milestone)
            else -> error("Milestone not supported")
        }
}