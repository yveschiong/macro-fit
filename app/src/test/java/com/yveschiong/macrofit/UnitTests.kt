package com.yveschiong.macrofit

import com.yveschiong.macrofit.rules.RxImmediateSchedulerRule
import org.junit.ClassRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

// Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
// framework dependencies or components.
@RunWith(MockitoJUnitRunner::class)
abstract class UnitTests {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule.create()
    }
}