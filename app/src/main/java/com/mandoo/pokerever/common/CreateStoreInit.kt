package com.mandoo.pokerever.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.mandoo.pokerever.R

@Stable
data class CreateStoreInfo(
    val id: String,
    val storeName: String = "",
    @DrawableRes
    val imageRes: Int = -1,
    val address: String = "",
    val point: String = "",
    var isLike: Boolean = false
)

object CreateStoreInit {
    private val createStoreList = mutableListOf<CreateStoreInfo>()

    init {
        with(createStoreList) {
            add(
                CreateStoreInfo(
                    "1",
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    "123"
                )
            )
            add(
                CreateStoreInfo(
                    "2",
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    "123"
                )
            )
            add(
                CreateStoreInfo(
                    "3",
                    "홀스",
                    R.drawable.holslogo,
                    "서울시 관악구 695-23",
                    "123"
                )
            )

            add(
                CreateStoreInfo(
                    "4",
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    "123"
                )
            )
            add(
                CreateStoreInfo(
                    "5",
                    "크라운",
                    R.drawable.krownlogo,
                    "서울시 관악구 695-23",
                    "123"
                )
            )

        }
    }

    // 좋아요 상태 업데이트 함수
    fun updateStoreLikeStatus(storeId: String, isLike: Boolean) {
        createStoreList.find { it.id == storeId }?.isLike = isLike
    }


    fun sortCreateStoreInfoList(): List<CreateStoreInfo> {
        // isLike true 값 설정 우선 정렬 후 storeId 값 기준으로 오름차순 정렬
        return createStoreList
            .sortedWith(compareByDescending<CreateStoreInfo> { it.isLike }.thenBy { it.id })
    }
    fun getStoreInfoById(storeId: String): CreateStoreInfo? {
        // CreateStoreList에서 id가 storeId인 항목을 찾아 반환
        return CreateStoreInit.sortCreateStoreInfoList().find { it.id == storeId }
    }
}