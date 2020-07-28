package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun reminderIsWorking() {
        val worker = TestListenableWorkerBuilder<ReminderWorker>(context).build()
        runBlocking {
            val result = worker.doWork()
            Assert.assertThat(result, `is`(ListenableWorker.Result.success()))
        }
    }
}