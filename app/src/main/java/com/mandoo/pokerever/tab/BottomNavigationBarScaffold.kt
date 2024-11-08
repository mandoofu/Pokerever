package com.mandoo.pokerever.tab

import android.util.Log
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
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // store_detail_screen이 아닐 때만 BottomNavigationBar 표시
            if (currentRoute != "store_detail_screen/{storeId}") {
                Log.d("bottomtab", currentRoute.toString())
                NavigationBar(
                    modifier = Modifier.height(56.dp),
                    containerColor = Color.Gray
                ) {
                    BottomNavigationItem().renderBottomNavigationItems()
                        .forEachIndexed { _, navigationItem ->
                            NavigationBarItem(
                                selected = navigationItem.route == currentRoute,
                                label = {
                                    Text(
                                        text = navigationItem.tabName,
                                        color = Color.White
                                    )
                                },
                                icon = {
                                    navigationItem.icon?.let {
                                        Icon(
                                            painter = it,
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
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenRouteDef.HomeTab.routeName,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(ScreenRouteDef.HomeTab.routeName) {
                HomeScreen(navController)
            }
            sliceNavGraph(navController)
        }
    }
}