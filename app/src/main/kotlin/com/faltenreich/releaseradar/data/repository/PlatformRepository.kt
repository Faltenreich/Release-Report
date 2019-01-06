package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.PlatformDao
import com.faltenreich.releaseradar.data.model.Platform

object PlatformRepository : Repository<Platform, PlatformDao>(PlatformDao)