package com.napos.stepper.example

import com.napos.stepper.core.MilestoneData
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.ExampleMilestoneProvider
import com.napos.stepper.example.steps.first.FirstData
import com.napos.stepper.example.steps.first.FirstMilestone
import com.napos.stepper.example.steps.first.FirstViewModel
import com.napos.stepper.example.steps.second.SecondData
import com.napos.stepper.example.steps.second.SecondMilestone
import com.napos.stepper.example.steps.second.SecondViewModel
import com.napos.stepper.example.steps.third.ThirdData
import com.napos.stepper.example.steps.third.ThirdMilestone
import com.napos.stepper.example.steps.third.ThirdViewModel
import com.napos.stepper.ui.screen.MilestoneScreenProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

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


val di = module {
    single { FirstViewModel() }
    single { SecondViewModel() }
    single { ThirdViewModel() }

    single {
        Roadmap {
            configuration = json
            milestones += listOf(FirstMilestone(), SecondMilestone(), ThirdMilestone())
        }
    }

    single<MilestoneScreenProvider> { ExampleMilestoneProvider() }
}

fun initializeKoin(customDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        customDeclaration()
        modules(di)
    }
}