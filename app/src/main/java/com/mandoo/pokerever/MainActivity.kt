package com.mandoo.pokerever

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mandoo.pokerever.basic.ui.LoginScreen
import com.mandoo.pokerever.basic.ui.RegisterScreen
import com.mandoo.pokerever.tab.BottomNavigationBarScaffold
import com.mandoo.pokerever.ui.theme.PokereverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokereverTheme {
                // Navigation Controller 설정
                val navController = rememberNavController()

                // NavHost로 화면 전환을 관리
                NavHost(
                    navController = navController,
                    startDestination = "login" // 첫 화면을 login으로 설정
                ) {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("register") {
                        RegisterScreen(navController)
                    }
                    composable("bottom_tab") {
                        BottomNavigationBarScaffold() // HomeScreen은 후에 추가
                    }
                }
            }
        }
    }
}

