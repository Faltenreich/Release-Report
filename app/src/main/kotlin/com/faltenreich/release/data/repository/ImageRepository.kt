package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.MediaDao
import com.faltenreich.release.data.model.Media

class ImageRepository(dao: MediaDao) : Repository<Media, MediaDao>(dao)