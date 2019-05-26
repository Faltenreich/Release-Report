package com.faltenreich.release.data

import com.faltenreich.release.extension.date
import com.faltenreich.release.extension.localDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import java.util.*

class DateTest {

    @Test
    fun `Successfully casts date to local date`() {
        val date = Date()
        val localDate = date.localDate
        assertEquals(date.year + 1900, localDate.year)
        assertEquals(date.month + 1, localDate.month.value)
        assertEquals(date.day, localDate.dayOfWeek.value % 7)
    }

    @Test
    fun `Successfully casts local date to date`() {
        val localDate = LocalDate.now()
        val date = localDate.date
        assertEquals(date.year + 1900, localDate.year)
        assertEquals(date.month + 1, localDate.month.value)
        assertEquals(date.day, localDate.dayOfWeek.value % 7)
    }
}