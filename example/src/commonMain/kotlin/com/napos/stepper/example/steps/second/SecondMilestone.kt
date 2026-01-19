package com.napos.stepper.example.steps.second

import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

class SecondMilestone(
    override var data: SecondData? = null,
    override var next: Milestone<*>? = null,
    override var previous: Milestone<*>? = null,
) : Milestone<SecondData>()