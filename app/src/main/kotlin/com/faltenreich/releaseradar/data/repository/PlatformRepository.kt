package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.PlatformDao
import com.faltenreich.releaseradar.data.model.Platform

class PlatformRepository(dao: PlatformDao) : Repository<Platform, PlatformDao>(dao)