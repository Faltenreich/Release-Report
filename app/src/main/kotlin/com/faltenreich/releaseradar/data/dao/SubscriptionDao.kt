package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Subscription
import com.faltenreich.releaseradar.data.provider.UserDependency

object SubscriptionDao : Dao<Subscription>(Subscription::class), UserDependency