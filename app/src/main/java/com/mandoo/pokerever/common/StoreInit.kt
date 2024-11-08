package com.mandoo.pokerever.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.mandoo.pokerever.R

@Stable
data class StoreInfo(
    val storeName: String = "",
    val storeNameRes: String = "",
    @DrawableRes
    val imageRes: Int = -1,
    val address: String = "",
    val addressRes: String = "",
    val distance: String = "",
    val distanceRes: Long
)

object StoreInit {
    private val storeList = mutableListOf<StoreInfo>()

    init {
        with(storeList) {
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    64
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    648
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    608
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    248
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    48
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    248
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    618
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    128
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    6048
                )
            )

            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    248
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    148
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    448
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    6248
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    6418
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    6438
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    1648
                )
            )
            add(
                StoreInfo(
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "거리 : ",
                    2648
                )
            )

        }
    }

    fun sortCreateStoreInfoList(): List<StoreInfo> {
        // distanceRes 값 기준으로 오름차순 정렬
        return storeList.sortedBy { it.distanceRes }
    }
}