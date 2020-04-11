package com.faltenreich.release.base.date

import org.junit.Assert
import org.junit.Test
import org.threeten.bp.LocalDate

class DateExtensionsTest {

    @Test
    fun isToday() {
        val today = LocalDate.now()
        Assert.assertTrue(today.isToday)
        val tomorrow = LocalDate.now().plusDays(1)
        Assert.assertFalse(tomorrow.isToday)
    }

    @Test
    fun isAfterOrEqual() {
        val today = LocalDate.now()
        val yesterday = LocalDate.now().minusDays(1)
        val tomorrow = LocalDate.now().plusDays(1)
        Assert.assertTrue(today.isAfterOrEqual(today))
        Assert.assertTrue(today.isAfterOrEqual(yesterday))
        Assert.assertFalse(today.isAfterOrEqual(tomorrow))
    }

    @Test
    fun isBeforeOrEqual() {
        val today = LocalDate.now()
        val yesterday = LocalDate.now().minusDays(1)
        val tomorrow = LocalDate.now().plusDays(1)
        Assert.assertTrue(today.isBeforeOrEqual(today))
        Assert.assertFalse(today.isBeforeOrEqual(yesterday))
        Assert.assertTrue(today.isBeforeOrEqual(tomorrow))
    }
}