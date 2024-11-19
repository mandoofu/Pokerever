package com.mandoo.pokerever.common

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.mandoo.pokerever.basic.ui.*
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

            // Firebase Auth에서 userId 가져오기
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (storeId != null && userId != null) {
                StoreDetailScreen(
                    navController = navController,
                    storeId = storeId,
                    userId = userId
                )
            } else {
                Toast.makeText(
                    context,
                    when {
                        storeId == null -> "유효한 매장 ID가 없습니다."
                        userId == null -> "사용자 인증이 필요합니다."
                        else -> "알 수 없는 오류가 발생했습니다."
                    },
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
