package com.mandoo.pokerever.common

import androidx.annotation.DrawableRes
import com.mandoo.pokerever.R
import kotlin.random.Random

data class CreateStoreInfo(
    val id: String,
    val storeName: String = "",
    val storeNameRes: String = "",
    @DrawableRes
    val imageRes: Int = -1,
    val address: String = "",
    val addressRes: String = "",
    val point: String = "",
    val pointRes: String = "",
    var isLike: Boolean = Random.nextBoolean()
)

object CreateStoreInit {
    private val createStoreList = mutableListOf<CreateStoreInfo>()

    init {
        with(createStoreList) {
            add(
                CreateStoreInfo(
                    "1",
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "포인트 : ",
                    "123"
                )
            )
            add(
                CreateStoreInfo(
                    "2",
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "포인트 : ",
                    "123"
                )
            )
            add(
                CreateStoreInfo(
                    "3",
                    "매장명 : ",
                    "홀스",
                    R.drawable.holslogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "포인트 : ",
                    "123"
                )
            )

            add(
                CreateStoreInfo(
                    "4",
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "포인트 : ",
                    "123"
                )
            )
            add(
                CreateStoreInfo(
                    "5",
                    "매장명 : ",
                    "크라운",
                    R.drawable.krownlogo,
                    "주소 : ",
                    "서울시 관악구 695-23",
                    "포인트 : ",
                    "123"
                )
            )

        }
    }

    fun shuffleCreateStoreInfoList(): MutableList<CreateStoreInfo> {
        createStoreList.shuffle()
        return createStoreList
    }

    fun getStoreInfoById(storeId: String): CreateStoreInfo? {
        // CreateStoreList에서 id가 storeId인 항목을 찾아 반환
        return CreateStoreInit.shuffleCreateStoreInfoList().find { it.id == storeId }
    }
}