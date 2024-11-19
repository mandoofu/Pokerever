package com.mandoo.pokerever.utils

import com.google.firebase.firestore.FirebaseFirestore

fun sendPoints(
    userId: String,
    storeId: String,
    points: Int,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    val userStoreRef = db.collection("users").document(userId).collection("addedStores").document(storeId)
    val storeRef = db.collection("stores").document(storeId)
    val storeUserRef = db.collection("stores").document(storeId).collection("users").document(userId)

    db.runTransaction { transaction ->
        // 사용자 서브컬렉션에서 현재 포인트 가져오기
        val userSnapshot = transaction.get(userStoreRef)
        val currentUserPoints = userSnapshot.getLong("points")?.toInt() ?: 0

        if (currentUserPoints < points) {
            throw Exception("보유 포인트가 부족합니다.")
        }

        // 매장 컬렉션에서 현재 포인트 가져오기
        val storeSnapshot = transaction.get(storeRef)
        val currentStorePoints = storeSnapshot.getLong("points")?.toInt() ?: 0

        // 매장 서브컬렉션에서 사용자 포인트 가져오기
        val storeUserSnapshot = transaction.get(storeUserRef)
        val currentStoreUserPoints = storeUserSnapshot.getLong("points")?.toInt() ?: 0

        // 업데이트
        transaction.update(userStoreRef, "points", currentUserPoints - points) // 사용자 포인트 차감
        transaction.update(storeRef, "points", currentStorePoints + points) // 매장 포인트 증가
        transaction.update(storeUserRef, "points", currentStoreUserPoints - points) // 매장 서브컬렉션 사용자 포인트 차감
    }.addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener { e ->
        onFailure(e.localizedMessage ?: "Failed to send points")
    }
}

