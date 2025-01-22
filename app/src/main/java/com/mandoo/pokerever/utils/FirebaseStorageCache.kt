package com.mandoo.pokerever.util

import com.google.firebase.storage.StorageReference

object FirebaseStorageCache {
    private val cache = mutableMapOf<String, String>()

    fun getCachedUrl(
        reference: StorageReference,
        onSuccess: (String?) -> Unit,
        onFailure: () -> Unit
    ) {
        val cachedUrl = cache[reference.path]
        if (cachedUrl != null) {
            onSuccess(cachedUrl)
        } else {
            reference.downloadUrl.addOnSuccessListener { uri ->
                cache[reference.path] = uri.toString()
                onSuccess(uri.toString())
            }.addOnFailureListener {
                onFailure()
            }
        }
    }
}
