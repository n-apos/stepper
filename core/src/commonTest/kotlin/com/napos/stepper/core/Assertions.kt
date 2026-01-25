package com.napos.stepper.core

import kotlin.test.assertTrue

inline fun <reified T> assertInvalidCast(block: () -> T) {
    var result: T? = null
    try {
        result = block()
    } catch (e: ClassCastException) {
        assertTrue(result !is T)
    } finally {
        assertTrue(result !is T)
    }
}