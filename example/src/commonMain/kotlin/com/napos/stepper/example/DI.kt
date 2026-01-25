package com.napos.stepper.example

import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.example.steps.ExampleMilestoneProvider
import com.napos.stepper.example.steps.ExampleRoadmap
import com.napos.stepper.example.steps.first.FirstViewModel
import com.napos.stepper.example.steps.second.SecondViewModel
import com.napos.stepper.example.steps.third.ThirdViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val di = module {
    single { FirstViewModel() }
    single { SecondViewModel() }
    single { ThirdViewModel() }

    single { ExampleRoadmap }

    single<MilestoneScreenProvider> { ExampleMilestoneProvider }
}

fun initializeKoin(customDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        customDeclaration()
        modules(di)
    }
}