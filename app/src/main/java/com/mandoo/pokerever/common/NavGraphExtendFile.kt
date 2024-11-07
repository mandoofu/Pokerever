package com.mandoo.pokerever.common

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mandoo.pokerever.basic.ui.InfoScreen
import com.mandoo.pokerever.basic.ui.StoreDetailScreen
import com.mandoo.pokerever.basic.ui.StoreScreen
import com.mandoo.pokerever.common.CreateStoreInit.getStoreInfoById
import com.mandoo.pokerever.tab.ScreenRouteDef

fun NavGraphBuilder.sliceNavGraph(navController: NavController) {
    /**
     * slice_nav_graph_name : 현재 분할된 navigation route 의 이름
     */
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
        // StoreDetailScreen 추가
        composable("store_detail_screen/{storeId}") { backStackEntry ->
            // storeId가 null일 경우 기본값을 설정하거나 오류 처리
            val storeId = backStackEntry.arguments?.getString("storeId")

            // storeId가 null인 경우를 처리
            if (storeId != null) {
                val storeInfo = getStoreInfoById(storeId) // storeId로 StoreInfo를 가져옴
                if (storeInfo != null) {
                    StoreDetailScreen(navController, storeInfo) // 정상적으로 StoreDetailScreen을 호출
                } else {
                    // StoreInfo가 null일 경우 Toast로 에러 메시지 표시
                    Toast.makeText(
                        LocalContext.current,
                        "매장 정보를 찾을 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // storeId가 null일 경우 Toast로 에러 메시지 표시
                Toast.makeText(
                    LocalContext.current,
                    "유효한 매장 ID가 없습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}