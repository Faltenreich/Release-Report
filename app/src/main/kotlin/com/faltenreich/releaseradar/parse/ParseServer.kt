package com.faltenreich.releaseradar.parse

import android.content.Context
import com.parse.Parse

object ParseServer {

    private const val APPLICATION_ID = "La20z4nOXJhy9FpOfJAsA4M4ucfsns6D1USzjXBj"
    private const val CLIENT_KEY = "Ftf9YKv3q7hqbTISMsuRPxMI1b7QyWYvewQZoacB"
    private const val SERVER_URL = "https://parseapi.back4app.com"

    fun init(context: Context) {
        val config = Parse.Configuration.Builder(context)
            .applicationId(APPLICATION_ID)
            .clientKey(CLIENT_KEY)
            .server(SERVER_URL)
            .build()
        Parse.initialize(config)
    }
}