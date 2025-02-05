package com.mandoo.pokerever

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.mandoo.pokerever.basic.ui.*
import com.mandoo.pokerever.location.LocationProvider
import com.mandoo.pokerever.permission.LocationPermissionHandler
import com.mandoo.pokerever.tab.BottomNavigationBarScaffold
import com.mandoo.pokerever.ui.theme.PokereverTheme
import com.mandoo.pokerever.viewmodel.StoreViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var storeViewModel: StoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeViewModel = StoreViewModel()

        val locationProvider = LocationProvider(this)
        val locationPermissionHandler = LocationPermissionHandler(
            activity = this,
            onPermissionGranted = {
                locationProvider.getCurrentLocation { location ->
                    if (location != null) {
                        storeViewModel.loadStoresWithDistance(
                            userLat = location.latitude,
                            userLon = location.longitude
                        )
                    } else {
                        Toast.makeText(this, "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onPermissionDenied = {
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        )
        locationPermissionHandler.checkAndRequestPermission()

        setContent {
            PokereverTheme {
                val navController = rememberNavController()
                var bottomNavVisible by remember { mutableStateOf(false) }

                // 현재 경로 감지 후 BottomNavigationBar 표시 여부 설정
                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        bottomNavVisible = when (backStackEntry.destination.route) {
                            "login", "register", "store_detail_screen/{storeId}" -> false
                            else -> true
                        }
                    }
                }

                Scaffold(
                    bottomBar = {
                        if (bottomNavVisible) {
                            BottomNavigationBarScaffold(navController)
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("bottom_tab") { BottomNavigationBarScaffold(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("store") { StoreScreen(navController) }
                        composable("info") { InfoScreen(navController) }
                        composable("store_detail_screen/{storeId}") { backStackEntry ->
                            val storeId = backStackEntry.arguments?.getString("storeId")
                            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                            if (storeId != null) {
                                StoreDetailScreen(navController, storeId, userId)
                            } else {
                                Toast.makeText(this@MainActivity, "유효한 매장 ID가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
