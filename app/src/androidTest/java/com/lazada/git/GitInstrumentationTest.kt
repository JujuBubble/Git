package com.lazada.git

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class GitInstrumentationTest {
    protected lateinit var app: InstrumentationTestApplication

    @Before
    fun getApps() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        this.app =
            instrumentation.targetContext.applicationContext as InstrumentationTestApplication

        assertEquals("com.lazada.git", (app as Application).packageName)
    }
}
