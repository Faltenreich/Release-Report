package com.faltenreich.release.domain.release.list

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateProvider
import org.threeten.bp.LocalDate

interface ReleaseProvider : DateProvider {

    val release: Release

    override fun equals(other:Any?): Boolean

    override val date: LocalDate?
        get() = release.releaseDate
}