package com.napos.stepper.compose.screen

import com.napos.stepper.core.Milestone

/**
 * A functional interface responsible for providing a [MilestoneScreen] for a given [Milestone].
 *
 * This provider acts as a factory to decouple the core roadmap logic from the specific UI implementation of each step.
 */
public fun interface MilestoneScreenProvider {

    /**
     * Provides a [MilestoneScreen] for the given [milestone].
     *
     * @param milestone The [Milestone] for which to provide a screen.
     * @return An instance of [MilestoneScreen] corresponding to the milestone.
     */
    public fun provide(milestone: Milestone<*>): MilestoneScreen<*, *>

    public companion object {
        /**
         * Allows creating a [MilestoneScreenProvider] from a lambda expression.
         *
         * This factory function provides a convenient DSL-like syntax for defining a provider.
         *
         * @param block A lambda that takes a [Milestone] and returns a [MilestoneScreen].
         * @return A new [MilestoneScreenProvider] instance.
         */
        public inline operator fun invoke(crossinline block: (milestone: Milestone<*>) -> MilestoneScreen<*, *>): MilestoneScreenProvider =
            MilestoneScreenProvider { block(it) }
    }
}
