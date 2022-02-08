package com.example.interiewprepandroidapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Before
    fun setup() {
        // run logic that we want before calling EACH test case
        // create new class instances, etc
    }

    @After
    fun tearDown() {
        // run logic taht we want AFTER calling each test case
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.interiewprepandroidapp", appContext.packageName)
    }

    @Test
    fun stringResourceSameAsString() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("InteriewPrepAndroidApp", appContext.resources.getString(R.string.app_name))
    }
}