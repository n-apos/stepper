package com.napos.stepper.example.steps.first

import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

public class FirstMilestone(
    override var data: FirstData? = null,
    override var next: Milestone<*>? = null,
    override var previous: Milestone<*>? = null,
) : Milestone<FirstData>() {

    override fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? FirstData
            ?: throw IllegalArgumentException("The data passed is not of type ${FirstData::class}")
    }

}