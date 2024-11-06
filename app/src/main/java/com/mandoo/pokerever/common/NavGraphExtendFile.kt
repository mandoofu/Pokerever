package com.mandoo.pokerever.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mandoo.pokerever.basic.ui.InfoScreen
import com.mandoo.pokerever.basic.ui.StoreScreen
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
    }
}