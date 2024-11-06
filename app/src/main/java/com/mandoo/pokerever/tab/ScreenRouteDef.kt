package com.mandoo.pokerever.tab

sealed class ScreenRouteDef(val routeName: String) {
    data object HomeTab : ScreenRouteDef("홈")
    data object InfoTab : ScreenRouteDef("내 정보")
    data object StoreTab : ScreenRouteDef("매장 정보")

    //분할된 Route Define
    sealed interface InnerContent {
        data object StoreList : ScreenRouteDef("store_list")
    }
}