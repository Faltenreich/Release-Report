package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.ImageDao
import com.faltenreich.releaseradar.data.model.Image

class ImageRepository(dao: ImageDao) : Repository<Image, ImageDao>(dao)