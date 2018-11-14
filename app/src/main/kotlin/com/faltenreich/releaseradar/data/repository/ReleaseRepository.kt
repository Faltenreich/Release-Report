package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.model.Release

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao)