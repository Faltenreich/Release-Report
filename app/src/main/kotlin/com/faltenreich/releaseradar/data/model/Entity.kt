package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity

abstract class Entity(
    override var id: String? = null
) : FirebaseEntity