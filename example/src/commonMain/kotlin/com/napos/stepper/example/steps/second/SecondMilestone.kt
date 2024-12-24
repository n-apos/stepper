package com.napos.stepper.example.steps.second

import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

public class SecondMilestone(
    override var data: SecondData? = null,
    override var next: Milestone<*>? = null,
    override var previous: Milestone<*>? = null,
) : Milestone<SecondData>() {

    override fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? SecondData
            ?: throw IllegalArgumentException("The data passed is not of type ${SecondData::class}")
    }

}