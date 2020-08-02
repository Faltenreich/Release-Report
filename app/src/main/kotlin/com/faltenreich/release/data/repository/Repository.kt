package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.Dao
import com.faltenreich.release.data.model.Model

interface Repository<T : Model>: Dao<T>