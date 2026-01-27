package com.napos.stepper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.serialization.json.Json

/**
 * Represents the complete journey or process, composed of a series of [Milestone]s.
 *
 * A Roadmap manages the state of the stepper, including the current step, navigation between steps,
 * and data aggregation.
 *
 * @property size The total number of milestones in the roadmap.
 * @property milestones An ordered list of all milestones in the roadmap.
 * @property current A [Flow] that emits the current [Milestone] whenever it changes.
 * @property currentIndex The index of the current milestone in the [milestones] list.
 */
public class Roadmap internal constructor(
    @PublishedApi
    internal val root: Milestone<*>, 
    @PublishedApi
    internal val json: Json,
    public val size: Int,
) {

    public val milestones: List<Milestone<*>>

    private var _current: MutableStateFlow<Milestone<*>> = MutableStateFlow(value = root)

    public val current: Flow<Milestone<*>> = _current
        .buffer()

    /**
     * Synchronously returns the current [Milestone].
     */
    public fun getCurrent(): Milestone<*> = _current.value

    public val currentIndex: Int
        get() {
            var index = 0
            var cursor: Milestone<*>? = root
            while (cursor != _current.value) {
                cursor = cursor?.next
                index++
            }
            return index
        }

    init {
        val milestones: MutableList<Milestone<*>> = mutableListOf()
        var current: Milestone<*>? = root
        while (current != null) {
            milestones.add(current)
            current = current.next
        }
        this.milestones = milestones
    }

    /**
     * Fills the current milestone with the provided [data].
     * @param data The [MilestoneData] to be set for the current step.
     */
    public fun fill(data: MilestoneData) {
        _current.value.fill(data)
    }

    /**
     * Navigates to the next milestone in the roadmap, if one exists.
     */
    public fun next() {
        _current.value.next?.let { next ->
            _current.value = next
        }
    }

    /**
     * Navigates to the previous milestone in the roadmap, if one exists.
     */
    public fun previous() {
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    /**
     * Clears the data of the current milestone and navigates to the previous one.
     */
    public fun rollback() {
        _current.value.data = null
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    /**
     * Resets the entire roadmap by clearing the data from all milestones.
     */
    public fun reset() {
        var cursor: Milestone<*>? = root
        while (cursor != null) {
            cursor.data = null
            cursor = cursor.next
        }
    }

    /**
     * Aggregates the data from all milestones into a single object of type [T].
     * This requires a [Json] configuration that can handle the serialization of all [MilestoneData] types.
     * @return An object of type [T] containing the aggregated data.
     */
    public inline fun <reified T> aggregate(): T =
        aggregate(root, json)

}

/**
 * A DSL builder for creating a [Roadmap] instance.
 *
 * @property configuration The [Json] configuration required for data serialization and aggregation.
 * @property milestones A mutable list of [Milestone]s to be included in the roadmap.
 */
public class RoadmapBuilder @PublishedApi internal constructor() {

    /**
     * Construction specific fields
     */

    private lateinit var root: Milestone<*>
    private lateinit var tip: Milestone<*>
    private var size: Int = 0


    /**
     * Builder specific fields, exposed publicly
     */
    public lateinit var configuration: Json
    public val milestones: MutableList<Milestone<*>> = mutableListOf()


    /**
     * Builder APIs
     */
    internal fun add(milestone: Milestone<*>) {
        if (!::root.isInitialized) {
            root = milestone
            tip = milestone
        } else {
            milestone.previous = tip
            tip.next = milestone
            tip = milestone
        }
        size++
    }

    internal fun create() {
        milestones.forEach { milestone ->
            add(milestone)
        }
    }


    /**
     * Builder performer action
     */
    internal fun build(): Roadmap {
        require(::configuration.isInitialized) {
            error("${::configuration.name} field should be initialized")
        }
        create()
        return Roadmap(root, configuration, size)
    }

}

/**
 * A DSL entry point for creating a [Roadmap].
 *
 * @param block A lambda with a [RoadmapBuilder] receiver to configure the roadmap.
 * @return A new [Roadmap] instance.
 */
public fun Roadmap(block: RoadmapBuilder.() -> Unit): Roadmap {
    val builder = RoadmapBuilder()
    builder.block()
    return builder.build()
}
