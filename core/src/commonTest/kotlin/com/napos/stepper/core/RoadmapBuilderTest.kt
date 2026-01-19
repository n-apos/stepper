package com.napos.stepper.core

import com.napos.stepper.core.steps.civil.CivilInformation
import com.napos.stepper.core.steps.civil.CivilMilestone
import com.napos.stepper.core.steps.contact.Contact
import com.napos.stepper.core.steps.contact.ContactMilestone
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.*

class RoadmapBuilderTest {

    private val civil = CivilMilestone()
    private val contact = ContactMilestone()

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        classDiscriminatorMode = ClassDiscriminatorMode.NONE
        serializersModule = SerializersModule {
            polymorphic(MilestoneData::class) {
                subclass(CivilInformation::class)
                subclass(Contact::class)
            }
        }
    }

    @Test
    fun test_add_function() {
        val builder = RoadmapBuilder()
        builder.configuration = json
        builder.add(civil)
        builder.add(contact)
        val roadmap = builder.build()

        assertEquals(2, roadmap.size)
        assertEquals(civil, roadmap.milestones[0])
        assertEquals(contact, roadmap.milestones[1])
        assertEquals(contact, civil.next)
        assertEquals(civil, contact.previous)
    }

    @Test
    fun test_create_function() {
        val builder = RoadmapBuilder()
        builder.configuration = json
        builder.milestones.add(civil)
        builder.milestones.add(contact)

        val roadmap = builder.build()

        assertEquals(2, roadmap.size)
        assertEquals(civil, roadmap.milestones[0])
        assertEquals(contact, roadmap.milestones[1])
        assertEquals(contact, civil.next)
        assertEquals(civil, contact.previous)
    }

    @Test
    fun test_build_function_with_public_dsl() {
        val roadmap = Roadmap {
            configuration = json
            milestones.add(civil)
            milestones.add(contact)
        }

        assertEquals(2, roadmap.size)
        assertEquals(civil, roadmap.milestones[0])
        assertEquals(contact, roadmap.milestones[1])
    }

    @Test
    fun test_build_fails_if_configuration_is_not_set() {
        val builder = RoadmapBuilder()
        builder.milestones.add(civil)

        val exception = assertFails {
            builder.build()
        }
        assertTrue(exception is IllegalStateException)
        assertTrue(exception.message!!.contains("configuration field should be initialized"))
    }

    @Test
    fun test_build_fails_with_empty_milestones() {
        val builder = RoadmapBuilder()
        builder.configuration = json

        assertFailsWith<RuntimeException> {
            builder.build()
        }
    }
}
