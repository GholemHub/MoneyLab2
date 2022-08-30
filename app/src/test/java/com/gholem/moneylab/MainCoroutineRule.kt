package com.gholem.moneylab

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

    @ExperimentalCoroutinesApi
    class MainCoroutineRule constructor(
        private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(
            TestCoroutineScheduler(),
            "test"
        )
    ) : TestWatcher() {
        val scope = TestScope(testDispatcher)
        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }
        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
        }
    }