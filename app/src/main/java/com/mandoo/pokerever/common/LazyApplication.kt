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

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // 메모리 캐시 최대 크기
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_image_cache"))
                    .maxSizePercent(0.05) // 디스크 캐시 최대 크기
                    .build()
            }
            .respectCacheHeaders(false) // 캐시 헤더 무시
            .crossfade(true) // 부드러운 이미지 전환
            .build()
    }
}
