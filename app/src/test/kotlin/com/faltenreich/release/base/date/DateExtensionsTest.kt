package com.faltenreich.release.base.date

import org.junit.Assert
import org.junit.Test

class DateExtensionsTest {

    @Test
    fun isToday() {
        val today = Now.localDate()
        Assert.assertTrue(today.isToday)
        val tomorrow = Now.localDate().plusDays(1)
        Assert.assertFalse(tomorrow.isToday)
    }

    @Test
    fun isAfterOrEqual() {
        val today = Now.localDate()
        val yesterday = Now.localDate().minusDays(1)
        val tomorrow = Now.localDate().plusDays(1)
        Assert.assertTrue(today.isAfterOrEqual(today))
        Assert.assertTrue(today.isAfterOrEqual(yesterday))
        Assert.assertFalse(today.isAfterOrEqual(tomorrow))
    }

    @Test
    fun isBeforeOrEqual() {
        val today = Now.localDate()
        val yesterday = Now.localDate().minusDays(1)
        val tomorrow = Now.localDate().plusDays(1)
        Assert.assertTrue(today.isBeforeOrEqual(today))
        Assert.assertFalse(today.isBeforeOrEqual(yesterday))
        Assert.assertTrue(today.isBeforeOrEqual(tomorrow))
    }
}