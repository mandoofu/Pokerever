package com.mandoo.pokerever.utils

import android.util.Log
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

    val userRef = db.collection("users").document(fromId)
        .collection("addedStores").document(toId)

    val storeRef = db.collection("stores").document(toId)

    val storeUserRef = db.collection("stores").document(toId)
        .collection("users").document(fromId)

    db.runTransaction { transaction ->
        val userSnapshot = transaction.get(userRef)
        val storeSnapshot = transaction.get(storeRef)
        val storeUserSnapshot = transaction.get(storeUserRef)

        val currentUserPoints = userSnapshot.getLong("points")?.toInt() ?: 0
        val currentStorePoints = storeSnapshot.getLong("points")?.toInt() ?: 0
        val currentStoreUserPoints = storeUserSnapshot.getLong("points")?.toInt() ?: 0

        if (currentUserPoints < points) {
            throw Exception("사용자 포인트 부족")
        }

        val newUserPoints = currentUserPoints - points
        val newStorePoints = currentStorePoints + points
        val newStoreUserPoints = currentStoreUserPoints - points

        if (newUserPoints != currentUserPoints) {
            transaction.update(userRef, "points", newUserPoints)
            Log.d("sendPoints", "userRef updated: $currentUserPoints → $newUserPoints")
        } else {
            Log.d("sendPoints", "⚠userRef points unchanged: $currentUserPoints")
        }

        if (newStorePoints != currentStorePoints) {
            transaction.update(storeRef, "points", newStorePoints)
            Log.d("sendPoints", "storeRef updated: $currentStorePoints → $newStorePoints")
        }

        if (newStoreUserPoints != currentStoreUserPoints) {
            transaction.update(storeUserRef, "points", newStoreUserPoints)
            Log.d("sendPoints", "storeUserRef updated: $currentStoreUserPoints → $newStoreUserPoints")
        }


        null // 트랜잭션 커밋 명시
    }.addOnSuccessListener {
        // 트랜잭션 성공 후 거래 기록 저장
        val transactionData = hashMapOf(
            "from" to fromId,
            "fromType" to fromType,
            "to" to toId,
            "toType" to toType,
            "points" to points,
            "timestamp" to Date().time
        )

        db.collection("transactions").document()
            .set(transactionData)
            .addOnSuccessListener {
                Log.d("sendPoints", "거래 기록 저장 성공")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("sendPoints", "거래 기록 저장 실패: ${e.message}", e)
                onFailure("포인트는 전송됐지만 기록 저장 실패")
            }

    }.addOnFailureListener { e ->
        Log.e("sendPoints", "포인트 전송 실패: ${e.message}", e)
        onFailure(e.message ?: "전송 실패")
    }
}
