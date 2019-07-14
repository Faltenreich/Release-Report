package com.faltenreich.release.data.dao

import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

interface ReleaseDao : Dao<Release> {
    fun getAll(date: LocalDate, onResult: (List<Release>) -> Unit)
    fun getByIds(ids: Collection<String>, type: ReleaseType?, startAt: LocalDate?, onResult: (List<Release>) -> Unit)
    fun getByIds(ids: Collection<String>, startAt: LocalDate, endAt: LocalDate, onResult: (List<Release>) -> Unit)
    fun getBetween(startAt: LocalDate, endAt: LocalDate, releaseType: ReleaseType, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit)
}