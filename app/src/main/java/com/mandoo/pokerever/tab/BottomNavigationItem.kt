package com.mandoo.pokerever.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.mandoo.pokerever.R

data class BottomNavigationItem(
    val tabName: String = "",
    val icon: Painter? = null,
    val route: String = ""
) {
    @Composable
    fun renderBottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                tabName = ScreenRouteDef.StoreTab.routeName,
                icon = painterResource(id = R.drawable.store_icon),
                route = ScreenRouteDef.StoreTab.routeName
            ),
            BottomNavigationItem(
                tabName = ScreenRouteDef.HomeTab.routeName,
                icon = painterResource(id = R.drawable.user_point_icon),
                route = ScreenRouteDef.HomeTab.routeName
            ),
            BottomNavigationItem(
                tabName = ScreenRouteDef.InfoTab.routeName,
                icon = painterResource(id = R.drawable.user_info_icon),
                route = ScreenRouteDef.InfoTab.routeName
            )
        )
    }
}