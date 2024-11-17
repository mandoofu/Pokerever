package com.mandoo.pokerever.common

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mandoo.pokerever.basic.ui.HomeScreen
import com.mandoo.pokerever.basic.ui.InfoScreen
import com.mandoo.pokerever.basic.ui.StoreDetailScreen
import com.mandoo.pokerever.basic.ui.StoreScreen
import com.mandoo.pokerever.utils.getStoreInfoById
import com.mandoo.pokerever.tab.ScreenRouteDef

fun NavGraphBuilder.sliceNavGraph(navController: NavController) {
    navigation(
        startDestination = ScreenRouteDef.InfoTab.routeName,
        route = "inner_content_nav_graph"
    ) {
        composable(ScreenRouteDef.InfoTab.routeName) {
            InfoScreen(navController)
        }
        composable(ScreenRouteDef.StoreTab.routeName) {
            StoreScreen(navController)
        }
        composable(ScreenRouteDef.HomeTab.routeName) {
            HomeScreen(navController)
        }
        composable("store_detail_screen/{storeId}") { backStackEntry ->
            val storeId = backStackEntry.arguments?.getString("storeId")
            val context = LocalContext.current

            var storeInfo by remember { mutableStateOf<StoreInfo?>(null) }
            var isLoading by remember { mutableStateOf(true) }

            // storeId를 사용해 매장 정보 가져오기
            LaunchedEffect(storeId) {
                if (storeId != null) {
                    getStoreInfoById(
                        storeId = storeId,
                        onSuccess = { info ->
                            storeInfo = info
                            isLoading = false
                        },
                        onFailure = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            isLoading = false
                        }
                    )
                } else {
                    Toast.makeText(context, "유효한 매장 ID가 없습니다.", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            }

            when {
                isLoading -> {
                    // 로딩 화면을 표시
                    androidx.compose.material3.Text(
                        text = "로딩 중...",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = androidx.compose.ui.Modifier.fillMaxSize()
                    )
                }
                storeInfo != null -> {
                    // StoreDetailScreen 호출
                    StoreDetailScreen(
                        navController = navController,
                        storeInfo = storeInfo!!,
                        userId = "userId" // 실제 사용자 ID로 대체
                    )
                }
                else -> {
                    Toast.makeText(context, "매장 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
