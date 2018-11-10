package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.dao.SubscriptionDao
import com.faltenreich.releaseradar.logging.log

class MainFragment : BaseFragment(R.layout.fragment_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SubscriptionDao.getAll(onSuccess = {
            log("Created subscription")
        }, onError = {
            log(it.message ?: "Fetch failed")
        })
    }
}