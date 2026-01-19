package com.napos.stepper.example.steps.first

import com.napos.stepper.core.Milestone

class FirstMilestone(
    override var data: FirstData? = null,
    override var next: Milestone<*>? = null,
    override var previous: Milestone<*>? = null,
) : Milestone<FirstData>()