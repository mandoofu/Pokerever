package com.mandoo.pokerever.basic.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mandoo.pokerever.MainActivity
import com.mandoo.pokerever.ui.theme.PokereverTheme

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokereverTheme {
                SplashScreen()
            }
        }

        // 일정 시간 후 MainActivity로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000) // 2초 지연
    }
}


