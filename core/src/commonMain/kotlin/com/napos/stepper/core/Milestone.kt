package com.napos.stepper.core

abstract class Milestone<T: MilestoneData> {
    abstract var data: T?
    abstract var previous: Milestone<*>?
    abstract var next: Milestone<*>?

    @Throws(IllegalArgumentException::class)
    abstract fun fill(milestoneData: MilestoneData)

}