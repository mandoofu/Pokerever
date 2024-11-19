package com.mandoo.pokerever.utils

import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

fun sendPoints(
    fromId: String,   // 로그인한 사용자 UID
    fromType: String, // 항상 "user"로 전달됨
    toId: String,     // 매장 ID
    toType: String,   // 항상 "store"로 전달됨
    points: Int,      // 전송할 포인트
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    val userRef = db.collection("users").document(fromId).collection("addedStores").document(toId) // 사용자 -> 매장
    val storeRef = db.collection("stores").document(toId) // 매장 메인 문서
    val storeUserRef = db.collection("stores").document(toId).collection("users").document(fromId) // 매장 -> 사용자
    val transactionsRef = db.collection("transactions").document() // 트랜잭션 기록

    db.runTransaction { transaction ->
        // 사용자 -> 매장 (addedStores)에서 포인트 가져오기
        val userSnapshot = transaction.get(userRef)
        val currentUserPoints = userSnapshot.getLong("points")?.toInt() ?: 0
        if (currentUserPoints < points) {
            throw Exception("사용자의 포인트가 부족합니다.")
        }

        // 매장 메인 문서에서 포인트 가져오기
        val storeSnapshot = transaction.get(storeRef)
        val currentStorePoints = storeSnapshot.getLong("points")?.toInt() ?: 0

        // 매장 -> 사용자 서브컬렉션에서 포인트 가져오기
        val storeUserSnapshot = transaction.get(storeUserRef)
        val currentStoreUserPoints = storeUserSnapshot.getLong("points")?.toInt() ?: 0

        // 업데이트: 사용자 -> 매장 포인트 차감
        transaction.update(userRef, "points", currentUserPoints - points)

        // 업데이트: 매장 메인 문서 포인트 증가
        transaction.update(storeRef, "points", currentStorePoints + points)

        // 업데이트: 매장 -> 사용자 포인트 차감
        transaction.update(storeUserRef, "points", currentStoreUserPoints - points)

        // 트랜잭션 기록 추가
        val transactionData = hashMapOf(
            "from" to fromId,
            "fromType" to fromType,
            "to" to toId,
            "toType" to toType,
            "points" to points,
            "timestamp" to Date().time
        )
        transaction.set(transactionsRef, transactionData)
    }.addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener { e ->
        onFailure(e.localizedMessage ?: "Failed to send points")
    }
}
