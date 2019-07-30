package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.PlatformDao
import com.faltenreich.release.data.model.Platform

object PlatformRepository : Repository<Platform, PlatformDao>(PlatformDao::class)