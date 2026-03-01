package com.napos.stepper.compose.screen

import androidx.compose.runtime.Composable
import com.napos.stepper.core.Milestone

/**
 * A base class for defining the UI of a single step ([Milestone]) in the stepper.
 *
 * This class connects a [Milestone] with its UI representation and a [MilestoneViewModel].
 * Each implementation of this class is responsible for rendering the content of a specific step.
 *
 * @param M The type of [Milestone] this screen represents.
 * @param VM The type of [MilestoneViewModel] associated with this screen, handling its UI logic.
 */
public abstract class MilestoneScreen<M : Milestone<*>, VM : MilestoneViewModel> {

    /**
     * The specific [Milestone] instance this screen represents.
     */
    protected abstract val milestone: M

    /**
     * The ViewModel associated with this screen, responsible for its business logic.
     */
    protected abstract val viewModel: VM

    /**
     * A composable function that returns the title to be displayed for this milestone.
     */
    @Composable
    public abstract fun title(): String

    /**
     * The main composable function that renders the primary content of the milestone screen.
     */
    @Composable
    public abstract fun render()

    @Composable
    public abstract fun preview()
}
