package com.faltenreich.release.framework.kotlinx

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonParser {

    @PublishedApi internal val parser = Json.Default

    inline fun <reified T> parseToJson(entity: T): String {
        return parser.encodeToString(entity)
    }

    inline fun <reified T : Any> parseFromJson(json: String): T {
        return parser.decodeFromString(json)
    }
}