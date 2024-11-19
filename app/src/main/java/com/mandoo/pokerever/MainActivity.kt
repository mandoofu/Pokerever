package com.mandoo.pokerever

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mandoo.pokerever.basic.ui.LoginScreen
import com.mandoo.pokerever.basic.ui.RegisterScreen
import com.mandoo.pokerever.location.LocationProvider
import com.mandoo.pokerever.permission.LocationPermissionHandler
import com.mandoo.pokerever.tab.BottomNavigationBarScaffold
import com.mandoo.pokerever.ui.theme.PokereverTheme
import com.mandoo.pokerever.viewmodel.StoreViewModel
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
                        // ViewModel에 사용자 위치를 전달
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

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("bottom_tab") { BottomNavigationBarScaffold() }
                }
            }
        }
    }
}
