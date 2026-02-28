package com.napos.stepper.core

import kotlin.reflect.KClass

/**
 * Represents a single step in a [Roadmap].
 *
 * A [Milestone] is a node in a doubly linked list, containing references to the [previous] and [next] milestones.
 * Each milestone holds optional [data] of type [T], which represents the information collected at that step.
 *
 * @param T The type of [MilestoneData] associated with this milestone.
 */
public abstract class Milestone<T : MilestoneData> {
    /**
     * The data collected at this milestone. Can be null if no data has been provided yet.
     */
    public abstract var data: T?

    /**
     * A reference to the previous milestone in the roadmap. Null if this is the first step.
     */
    public abstract var previous: Milestone<*>?

    /**
     * A reference to the next milestone in the roadmap. Null if this is the last step.
     */
    public abstract var next: Milestone<*>?

    @Suppress("UNCHECKED_CAST")
    private val _clazz: KClass<T> = this::class as KClass<T>

    /**
     * Fills the milestone with the provided [milestoneData].
     *
     * This function performs a type check to ensure that the provided data is of the correct type [T].
     *
     * @param milestoneData The data to fill the milestone with.
     * @throws IllegalArgumentException if the provided data is of the wrong type.
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    public fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? T
            ?: error("Wrong data type, expected : ${_clazz.simpleName} but got : ${milestoneData::class.simpleName}")
    }

}
