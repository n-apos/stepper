package com.napos.stepper.core.steps.contact

import com.napos.stepper.core.Milestone

class ContactMilestone(
    override var data: Contact? = null,
    override var previous: Milestone<*>? = null,
    override var next: Milestone<*>? = null,
) : Milestone<Contact>()