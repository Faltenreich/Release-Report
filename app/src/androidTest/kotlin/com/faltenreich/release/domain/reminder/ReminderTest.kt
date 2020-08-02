package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.atStartOfWeek
import com.faltenreich.release.data.dao.factory.DaoFactory
import com.faltenreich.release.data.dao.factory.DemoDaoProvider
import com.faltenreich.release.data.repository.ReleaseRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Clock
import org.threeten.bp.ZoneOffset

@RunWith(AndroidJUnit4::class)
class ReminderTest {

    private lateinit var context: Context
    private val worker by lazy { TestListenableWorkerBuilder<ReminderWorker>(context).build() }
    private val uiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        // FIXME: Too late to change provider
        DaoFactory.provider = DemoDaoProvider()
    }

    @After
    fun cleanup() {
        Now.clock = Clock.systemDefaultZone()
    }

    @Test
    fun isSucceeding() {
        runBlocking {
            val result = worker.doWork()
            Assert.assertThat(result, `is`(ListenableWorker.Result.success()))
        }
    }

    @Test
    fun isShowingLocalNotificationForWeeklyReleasesAtStartOfWeek() {
        val startOfWeek = Now.localDate().atStartOfWeek.atTime(8, 0).toInstant(ZoneOffset.UTC)
        Now.clock = Clock.fixed(startOfWeek, ZoneOffset.UTC)

        runBlocking {
            worker.doWork()

            val notificationAppName = uiDevice.findObject(UiSelector()
                .resourceId("android:id/app_name_text")
                .text(context.getString(R.string.app_name)))
            Assert.assertTrue(notificationAppName.exists())

            val notificationTitle = uiDevice.findObject(UiSelector()
                .resourceId("android:id/title")
                .text(context.getString(R.string.reminder_weekly_notification)))
            Assert.assertTrue(notificationTitle.exists())
        }
    }

    @Test
    fun isShowingLocalNotificationForSubscriptions() {
        runBlocking {
            val favorites = ReleaseRepository.getAfter(
                Now.localDate(),
                0,
                1
            )
            val favorite = favorites.first()
            ReleaseRepository.subscribe(favorite)

            worker.doWork()

            val notificationAppName = uiDevice.findObject(UiSelector()
                .resourceId("android:id/app_name_text")
                .text(context.getString(R.string.app_name)))
            Assert.assertTrue(notificationAppName.exists())

            val notificationTitle = uiDevice.findObject(UiSelector()
                .resourceId("android:id/title")
                .textContains(context.getString(R.string.reminder_subscriptions_notification)))
            Assert.assertTrue(notificationTitle.exists())
        }
    }
}