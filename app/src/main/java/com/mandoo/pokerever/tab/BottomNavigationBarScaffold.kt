package com.mandoo.pokerever.tab

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mandoo.pokerever.R

@Composable
fun BottomNavigationBarScaffold(navController: NavController) {
    val items = listOf(
        BottomNavigationItem("매장", R.drawable.store_icon, "store"),
        BottomNavigationItem("홈", R.drawable.user_point_icon, "home"),
        BottomNavigationItem("내 정보", R.drawable.user_info_icon, "info")
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // 현재 선택된 탭을 remember 상태로 관리
    var selectedRoute by remember { mutableStateOf(currentRoute) }

    // NavController 변경 사항 감지 후 상태 업데이트
    LaunchedEffect(currentRoute) {
        selectedRoute = currentRoute
    }

    NavigationBar(
        modifier = Modifier.height(56.dp),
        containerColor = Color.Gray
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == selectedRoute,
                label = { Text(text = item.tabName, color = Color.White) },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.tabName,
                        modifier = Modifier.size(28.dp),
                        tint = if (item.route == selectedRoute) Color.Yellow else Color.White
                    )
                },
                onClick = {
                    if (selectedRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavigationItem(val tabName: String, val icon: Int, val route: String)
