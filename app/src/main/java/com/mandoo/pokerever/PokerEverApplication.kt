package com.mandoo.pokerever

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokerEverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}