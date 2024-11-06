package com.mandoo.pokerever.tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mandoo.pokerever.basic.ui.HomeScreen
import com.mandoo.pokerever.common.sliceNavGraph

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            /**
             * Bottom Bar 구성 하기
             */
            NavigationBar(
                modifier = Modifier
                    .height(56.dp),
                containerColor = Color.Gray
            ) {
                BottomNavigationItem().renderBottomNavigationItems()
                    .forEachIndexed { _, navigationItem ->
                        NavigationBarItem(
                            selected = navigationItem.route == currentDestination?.route,
                            label = {
                                Text(
                                    text = navigationItem.tabName,
                                    color = Color.White
                                )
                            },
                            icon = {
                                navigationItem.icon?.let {
                                    Icon(
                                        painter = it, // painter 설정된 icon 사용
                                        contentDescription = navigationItem.tabName,
                                        modifier = Modifier.size(28.dp),
                                        tint = Color.White
                                    )
                                }
                            },
                            onClick = {
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )

                    }
            }
        }
    ) { paddingValues -> //Scaffold Content 의 Padding 컴포즈
        NavHost(
            navController = navController,
            startDestination = ScreenRouteDef.HomeTab.routeName, //홈을 시작 탭으로 설정
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(ScreenRouteDef.HomeTab.routeName) {
                HomeScreen(
                    navController
                )
            }
            /**
             * Navigation Graph 를 분할
             */
            sliceNavGraph(navController)
        }
    }
}
