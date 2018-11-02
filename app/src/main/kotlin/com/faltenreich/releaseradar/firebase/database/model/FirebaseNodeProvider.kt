package com.faltenreich.releaseradar.data.model

internal interface FirebaseNodeProvider <MODEL : FirebaseEntity> {

    val nodeName: String

    val nodeRootPath: String
        get() = ""

    fun buildPath(): String = "$nodeRootPath/$nodeName"

    fun buildPath(id: String): String = "${buildPath()}/$id"

    fun buildPath(entity: MODEL): String = entity.id?.let { id -> buildPath(id) } ?: throw Exception("Failed to build path for id that is null")
}