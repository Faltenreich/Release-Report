package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.MediaDao
import com.faltenreich.release.data.model.Media

class MediaRepository(dao: MediaDao) : Repository<Media, MediaDao>(dao)