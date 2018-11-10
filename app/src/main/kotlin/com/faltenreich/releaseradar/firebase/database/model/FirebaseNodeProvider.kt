package com.faltenreich.releaseradar.firebase.database.model

internal interface FirebaseNodeProvider <MODEL : FirebaseEntity> {

    val nodeRootPath: String

    val nodeName: String

    fun buildPath(): String = "$nodeRootPath/$nodeName"

    fun buildPath(id: String): String = "${buildPath()}/$id"

    fun buildPath(entity: MODEL): String = entity.id?.let { id -> buildPath(id) } ?: throw Exception("Failed to build path for entity with id that is null")
}