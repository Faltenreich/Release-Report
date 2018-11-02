package com.faltenreich.releaseradar.firebase.database.operation

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference

internal data class FirebaseWriteOperation(
    override val path: String,
    override val onSuccess: ((Unit) -> Unit)?,
    override val onError: ((Exception) -> Unit)?,
    override val operation: (DatabaseReference) -> Task<Void>
) : FirebaseOperation<Unit, DatabaseReference, Task<Void>, (DatabaseReference) -> Task<Void>>
