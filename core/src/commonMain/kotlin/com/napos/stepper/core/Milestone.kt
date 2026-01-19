package com.napos.stepper.core

import kotlin.reflect.KClass

abstract class Milestone<T: MilestoneData> {
    abstract var data: T?
    abstract var previous: Milestone<*>?
    abstract var next: Milestone<*>?

    private val _clazz: KClass<T> = this::class as KClass<T>

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    fun fill(milestoneData: MilestoneData) {
        data = milestoneData as? T
            ?: error("Wrong data type, expected : ${_clazz.simpleName} but got : ${milestoneData::class.simpleName}")
    }

}