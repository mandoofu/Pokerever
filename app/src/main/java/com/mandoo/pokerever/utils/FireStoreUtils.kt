package com.mandoo.pokerever.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.mandoo.pokerever.common.StoreInfo

fun getStoreInfoById(
    storeId: String,
    onSuccess: (StoreInfo?) -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val storeRef = db.collection("stores").document(storeId)

    storeRef.get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val storeInfo = document.toObject<StoreInfo>()
                onSuccess(storeInfo)
            } else {
                onSuccess(null)
            }
        }
        .addOnFailureListener { e ->
            onFailure(e.localizedMessage ?: "Failed to fetch store info")
        }
}
fun addStoreToUser(userId: String, storeId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    val addedStoreData = hashMapOf(
        "points" to 0 // 초기 포인트 설정
    )

    userRef.collection("addedStores").document(storeId)
        .set(addedStoreData)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onFailure(e.localizedMessage ?: "Failed to add store")
        }
}

fun fetchUserAddedStores(userId: String, onResult: (List<Pair<String, Int>>) -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    userRef.collection("addedStores")
        .get()
        .addOnSuccessListener { result ->
            val addedStores = result.documents.map { document ->
                val storeId = document.id
                val points = document.getLong("points")?.toInt() ?: 0
                storeId to points
            }
            onResult(addedStores)
        }
        .addOnFailureListener { e ->
            onFailure(e.localizedMessage ?: "Failed to fetch user added stores")
        }
}

fun updateUserPoints(userId: String, storeId: String, delta: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userStoreRef = db.collection("users").document(userId).collection("addedStores").document(storeId)

    db.runTransaction { transaction ->
        val snapshot = transaction.get(userStoreRef)
        val currentPoints = snapshot.getLong("points")?.toInt() ?: 0
        val updatedPoints = currentPoints + delta
        transaction.update(userStoreRef, "points", updatedPoints)
    }.addOnSuccessListener { onSuccess() }
        .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "Failed to update points") }
}


fun initializeUserStorePoints(userId: String, storeId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val pointsRef = db.collection("user_store_points").document()

    val pointsData = hashMapOf(
        "userId" to userId,
        "storeId" to storeId,
        "points" to 0
    )

    pointsRef.set(pointsData)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "Failed to initialize points") }
}

