package com.faltenreich.release.data.dao

import com.faltenreich.release.data.enum.MediaType
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

interface ReleaseDao : Dao<Release> {
    fun getAll(startAt: LocalDate, greaterThan: Boolean, minPopularity: Float, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun getByIds(ids: Collection<String>, type: MediaType?, startAt: LocalDate?, onResult: (List<Release>) -> Unit)
    fun getBetween(startAt: LocalDate, endAt: LocalDate, mediaType: MediaType, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit)
}