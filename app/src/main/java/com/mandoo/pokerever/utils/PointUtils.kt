package com.mandoo.pokerever.utils

import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

fun sendPoints(
    fromId: String,
    fromType: String, // "user" 또는 "store"
    toId: String,
    toType: String,   // "user" 또는 "store"
    points: Int,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    val fromRef = if (fromType == "user") {
        db.collection("users").document(fromId).collection("addedStores").document(toId)
    } else {
        db.collection("stores").document(fromId).collection("users").document(toId)
    }

    val toRef = if (toType == "user") {
        db.collection("users").document(toId).collection("addedStores").document(fromId)
    } else {
        db.collection("stores").document(toId).collection("users").document(fromId)
    }

    val transactionsRef = db.collection("transactions").document()

    db.runTransaction { transaction ->
        // 보낸 사람 포인트 가져오기
        val fromSnapshot = transaction.get(fromRef)
        val currentFromPoints = fromSnapshot.getLong("points")?.toInt() ?: 0

        if (currentFromPoints < points) {
            throw Exception("보유 포인트가 부족합니다.")
        }

        // 받는 사람 포인트 가져오기
        val toSnapshot = transaction.get(toRef)
        val currentToPoints = toSnapshot.getLong("points")?.toInt() ?: 0

        // 업데이트
        transaction.update(fromRef, "points", currentFromPoints - points) // 보낸 사람 포인트 차감
        transaction.update(toRef, "points", currentToPoints + points)     // 받는 사람 포인트 증가

        // 트랜잭션 기록 추가
        val transactionData = hashMapOf(
            "from" to fromId,
            "fromType" to fromType, // "user" 또는 "store"
            "to" to toId,
            "toType" to toType,     // "user" 또는 "store"
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
