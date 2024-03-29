package com.faltenreich.release.data

import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.date
import com.faltenreich.release.base.date.localDate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateTest {

    @Test
    fun `Date can be converted to LocalDate`() {
        val date = Date()
        val localDate = date.localDate
        assertEquals(date.year + 1900, localDate.year)
        assertEquals(date.month + 1, localDate.month.value)
        assertEquals(date.day, localDate.dayOfWeek.value % 7)
    }

    @Test
    fun `LocalDate can be converted to Date`() {
        val localDate = Now.localDate()
        val date = localDate.date
        assertEquals(date.year + 1900, localDate.year)
        assertEquals(date.month + 1, localDate.month.value)
        assertEquals(date.day, localDate.dayOfWeek.value % 7)
    }
}