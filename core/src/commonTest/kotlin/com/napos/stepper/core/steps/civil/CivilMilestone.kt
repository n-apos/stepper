package com.napos.stepper.core.steps.civil

import com.napos.stepper.core.Milestone

class CivilMilestone(
    override var data: CivilInformation? = null,
    override var previous: Milestone<*>? = null,
    override var next: Milestone<*>? = null,
) : Milestone<CivilInformation>()
