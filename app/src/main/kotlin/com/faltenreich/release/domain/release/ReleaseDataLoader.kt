package com.faltenreich.release.domain.release

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

interface ReleaseDataLoader {
    suspend fun getBefore(date: LocalDate, page: Int, pageSize: Int): List<Release>
    suspend fun getAfter(date: LocalDate, page: Int, pageSize: Int): List<Release>
}