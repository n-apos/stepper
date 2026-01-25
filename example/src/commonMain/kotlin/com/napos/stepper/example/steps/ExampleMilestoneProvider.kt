package com.napos.stepper.example.steps

import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.example.steps.first.FirstMilestone
import com.napos.stepper.example.steps.first.FirstScreen
import com.napos.stepper.example.steps.second.SecondMilestone
import com.napos.stepper.example.steps.second.SecondScreen
import com.napos.stepper.example.steps.third.ThirdMilestone
import com.napos.stepper.example.steps.third.ThirdScreen

val ExampleMilestoneProvider = MilestoneScreenProvider { milestone ->
    when (milestone) {
        is FirstMilestone -> FirstScreen(milestone)
        is SecondMilestone -> SecondScreen(milestone)
        is ThirdMilestone -> ThirdScreen(milestone)
        else -> error("Milestone not supported")
    }
}