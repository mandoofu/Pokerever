package com.mandoo.pokerever.common

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache

class LazyApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        lazyApplication = this
    }

    companion object {
        private lateinit var lazyApplication: Application
        fun getLazyApplication() = lazyApplication
    }

    /**
     * Tutor Pyo
     * Coil Image Cache Memory Custom
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.30)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_image_cache"))
                    .maxSizePercent(0.1)
                    .build()
            }
            //.logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}