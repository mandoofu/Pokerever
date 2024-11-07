package com.mandoo.pokerever.common

import androidx.annotation.DrawableRes
import com.mandoo.pokerever.R
import kotlin.random.Random

data class StoreInfo(
    val storeName: String = "",
    val storeNameRes: String = "",
    @DrawableRes
    val imageRes: Int = -1,
    val address: String = "",
    val addressRes: String = "",
    val distance: String = "",
    val distanceRes: String = "",
    var isLike: Boolean = Random.nextBoolean()
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
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
                    "648m"
                )
            )

        }
    }

    fun shuffleStoreInfoList(): MutableList<StoreInfo> {
        storeList.shuffle()
        return storeList
    }
}