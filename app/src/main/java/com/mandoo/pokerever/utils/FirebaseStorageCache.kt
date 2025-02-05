package com.mandoo.pokerever.utils

import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await
import java.util.concurrent.ConcurrentHashMap

object FirebaseStorageCache {
    private val cache = ConcurrentHashMap<String, String>()

    suspend fun getCachedUrl(
        reference: StorageReference,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        val path = reference.path

        // 캐시에 존재하면 즉시 반환
        cache[path]?.let {
            onSuccess(it)
            return
        }
        // Firebase에서 URL 가져오기 (캐시 없음)
        try {
            val url = withContext(Dispatchers.IO) { reference.downloadUrl.await().toString() }
            cache[path] = url // 캐싱
            onSuccess(url)
        } catch (e: Exception) {
            onFailure()
        }
    }
}