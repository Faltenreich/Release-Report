package com.faltenreich.releaseradar.helper

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import com.faltenreich.releaseradar.extension.log

object IdlingResource {

    private const val IDLING_RESOURCE_NAME = "idlingResource"

    private val idlingResource: CountingIdlingResource by lazy { CountingIdlingResource(IDLING_RESOURCE_NAME) }

    fun register() = IdlingRegistry.getInstance().register(idlingResource)

    fun unregister() = IdlingRegistry.getInstance().unregister(idlingResource)

    fun increment() = idlingResource.increment()

    fun decrement(): Any = when (!idlingResource.isIdleNow) {
        true -> idlingResource.decrement()
        false -> log("Failed to decrement idling resource counter")
    }
}
