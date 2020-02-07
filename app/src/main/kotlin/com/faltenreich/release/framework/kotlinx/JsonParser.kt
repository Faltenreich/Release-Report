package com.faltenreich.release.framework.kotlinx

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

object JsonParser {

    private val parser = Json(JsonConfiguration.Stable)

    fun <T> parseToJson(serializer: KSerializer<T>, entity: T): String {
        return parser.stringify(serializer, entity)
    }

    fun <T : Any> parseFromJson(serializer: KSerializer<T>, json: String): T {
        return parser.parse(serializer, json)
    }
}