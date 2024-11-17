package com.mandoo.pokerever.utils

import com.google.firebase.firestore.FirebaseFirestore

fun sendPoints(userId: String, storeId: String, points: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val pointsRef = db.collection("user_store_points").document()

    val transaction = hashMapOf(
        "userId" to userId,
        "storeId" to storeId,
        "transactionType" to "send",
        "points" to points,
        "timestamp" to System.currentTimeMillis()
    )

    pointsRef.set(transaction)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "Failed to send points") }
}
