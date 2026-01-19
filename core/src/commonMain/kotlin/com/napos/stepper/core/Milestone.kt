package com.napos.stepper.core

import kotlin.reflect.KClass

public abstract class Milestone<T: MilestoneData> {
    public abstract var data: T?
    public abstract var previous: Milestone<*>?
    public abstract var next: Milestone<*>?

    private val _clazz: KClass<T> = this::class as KClass<T>

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    public fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? T
            ?: error("Wrong data type, expected : ${_clazz.simpleName} but got : ${milestoneData::class.simpleName}")
    }

}