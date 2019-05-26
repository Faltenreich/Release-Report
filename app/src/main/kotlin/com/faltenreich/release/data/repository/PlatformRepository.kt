package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.PlatformDao
import com.faltenreich.release.data.model.Platform

class PlatformRepository(dao: PlatformDao) : Repository<Platform, PlatformDao>(dao)