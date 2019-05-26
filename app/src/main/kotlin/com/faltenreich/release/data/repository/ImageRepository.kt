package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.ImageDao
import com.faltenreich.release.data.model.Image

class ImageRepository(dao: ImageDao) : Repository<Image, ImageDao>(dao)