package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.atStartOfWeek
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Clock
import org.threeten.bp.ZoneOffset

@RunWith(AndroidJUnit4::class)
class ReminderTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val startOfWeek = Now.localDate().atStartOfWeek.atTime(8, 0).toInstant(ZoneOffset.UTC)
        Now.clock = Clock.fixed(startOfWeek, ZoneOffset.UTC)
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