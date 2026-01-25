package com.napos.stepper.compose

import com.napos.stepper.core.MilestoneData
import com.napos.stepper.core.Roadmap
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

fun createRoadmap(): Roadmap = Roadmap {
    configuration = Json {
        serializersModule = SerializersModule {
            polymorphic(MilestoneData::class) {
                subclass(StepData::class)
            }
        }
    }
    milestones += TestMilestone(StepData("Step 1"), null, null)
    milestones += TestMilestone(StepData("Step 2"), null, null)
    milestones += TestMilestone(StepData("Step 3"), null, null)
}