package com.napos.stepper.core.steps.contact

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData

class ContactMilestone(
    override var data: Contact? = null,
    override var previous: Milestone<*>? = null,
    override var next: Milestone<*>? = null,
) : Milestone<Contact>() {

    override fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? Contact
            ?: throw IllegalArgumentException("The submitted step data is not of type ${Contact::class}")
    }
}