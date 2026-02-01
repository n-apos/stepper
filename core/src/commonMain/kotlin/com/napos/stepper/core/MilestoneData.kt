package com.napos.stepper.core

import kotlinx.serialization.Serializable

/**
 * An abstract class representing the data associated with a [Milestone].
 *
 * This class is marked as [Serializable] to allow for data persistence and state restoration.
 * Implementations of this class should define the specific data fields required for a particular step in the roadmap.
 */
@Serializable
public abstract class MilestoneData