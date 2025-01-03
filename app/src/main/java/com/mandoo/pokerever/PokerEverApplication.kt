package com.mandoo.pokerever

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokerEverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true)
    }
}