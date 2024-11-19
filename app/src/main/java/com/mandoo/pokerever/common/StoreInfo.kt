package com.mandoo.pokerever.common


import com.google.firebase.firestore.GeoPoint

data class StoreInfo(
    val sid: String = "",
    val storeName: String = "",
    val address: String = "",
    val imageRes: String = "",
    val distance: Double = 0.0, // 계산된 거리
    val geoPoint: GeoPoint? = null, // Firestore에서 매핑될 GeoPoint 필드
    val points: Int = 0
)