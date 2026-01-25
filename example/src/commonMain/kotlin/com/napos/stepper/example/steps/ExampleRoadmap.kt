package com.napos.stepper.example.steps

import com.napos.stepper.core.MilestoneData
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.first.FirstData
import com.napos.stepper.example.steps.first.FirstMilestone
import com.napos.stepper.example.steps.second.SecondData
import com.napos.stepper.example.steps.second.SecondMilestone
import com.napos.stepper.example.steps.third.ThirdData
import com.napos.stepper.example.steps.third.ThirdMilestone
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@OptIn(ExperimentalSerializationApi::class)
private val json: Json = Json {
    classDiscriminatorMode = ClassDiscriminatorMode.NONE
    serializersModule = SerializersModule {
        polymorphic(MilestoneData::class) {
            subclass(FirstData::class)
            subclass(SecondData::class)
            subclass(ThirdData::class)
        }
    }
}


val ExampleRoadmap = Roadmap {
    configuration = json
    milestones += listOf(FirstMilestone(), SecondMilestone(), ThirdMilestone())
}