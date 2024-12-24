package com.napos.stepper.example.steps.third

import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

public class ThirdMilestone(
    override var data: ThirdData? = null,
    override var previous: Milestone<*>? = null,
    override var next: Milestone<*>? = null,
) : Milestone<ThirdData>() {

    override fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? ThirdData
            ?: throw IllegalArgumentException("")
    }

}