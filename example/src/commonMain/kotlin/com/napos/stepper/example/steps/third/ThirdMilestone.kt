package com.napos.stepper.example.steps.third

import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

class ThirdMilestone(
    override var data: ThirdData? = null,
    override var previous: Milestone<*>? = null,
    override var next: Milestone<*>? = null,
) : Milestone<ThirdData>()