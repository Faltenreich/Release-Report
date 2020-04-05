package com.faltenreich.release.framework.parse

import android.content.Context
import com.faltenreich.release.BuildConfig
import com.parse.Parse

object ParseServer {

    fun init(context: Context) {
        val config = Parse.Configuration.Builder(context)
            .server(BuildConfig.PARSE_SERVER_URL)
            .applicationId(BuildConfig.PARSE_APPLICATION_ID)
            .clientKey(BuildConfig.PARSE_CLIENT_KEY)
            .build()
        Parse.initialize(config)
    }
}