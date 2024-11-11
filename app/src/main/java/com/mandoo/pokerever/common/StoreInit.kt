package com.mandoo.pokerever.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.mandoo.pokerever.R

@Stable
data class StoreInfo(
    val storeName: String = "",
    @DrawableRes
    val imageRes: Int = -1,
    val address: String = "",
    val distance: Long
)

object StoreInit {
    private val storeList = mutableListOf<StoreInfo>()

    init {
        with(storeList) {
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    64
                )
            )
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    6467
                )
            )
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    6423
                )
            )
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    6434
                )
            )
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    641
                )
            )
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    642
                )
            )
            add(
                StoreInfo(
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    645
                )
            )


            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    248
                )
            )
            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    7623
                )
            )
            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    1235
                )
            )
            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    5643
                )
            )
            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    23
                )
            )
            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    1234
                )
            )
            add(
                StoreInfo(
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    76543
                )
            )


        }
    }

    fun sortCreateStoreInfoList(): List<StoreInfo> {
        // distanceRes 값 기준으로 오름차순 정렬
        return storeList.sortedBy { it.distance }
    }
}