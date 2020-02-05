package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.DaoFactory
import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release

object ReleaseRepository : Repository<Release>(), ReleaseDao by DaoFactory.dao(ReleaseDao::class)