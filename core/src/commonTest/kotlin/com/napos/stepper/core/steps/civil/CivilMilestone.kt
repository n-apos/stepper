package com.napos.stepper.core.steps.civil

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

class CivilMilestone(
    override var data: CivilInformation? = null,
    override var previous: Milestone<*>? = null,
    override var next: Milestone<*>? = null,
) : Milestone<CivilInformation>() {

    override fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? CivilInformation
            ?: throw IllegalArgumentException("The submitted step data is not of type ${CivilInformation::class}")
    }
}
