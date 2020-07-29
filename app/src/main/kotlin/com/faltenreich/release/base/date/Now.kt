package com.faltenreich.release.base.date

import org.threeten.bp.Clock
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

object Now {

    var clock: Clock = Clock.systemDefaultZone()

    fun localDateTime(): LocalDateTime {
        return LocalDateTime.now(clock)
    }

    fun localDate(): LocalDate {
        return LocalDate.now(clock)
    }
}