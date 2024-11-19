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

fun fetchUserAddedStores(
    userId: String,
    onResult: (List<StoreInfo>) -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    db.collection("users").document(userId).collection("addedStores")
        .get()
        .addOnSuccessListener { result ->
            val addedStores = result.map { doc ->
                doc.toObject(StoreInfo::class.java).copy(sid = doc.id)
            }
            onResult(addedStores)
        }
        .addOnFailureListener { e ->
            onFailure(
                e.localizedMessage ?: "Failed to fetch user added stores"
            )
        }
}

fun updateUserPoints(userId: String, storeId: String, delta: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    val userStoreRef = db.collection("users").document(userId).collection("addedStores").document(storeId)
    val storeUserRef =
        db.collection("stores").document(storeId).collection("users").document(userId)

    db.runTransaction { transaction ->
        val userSnapshot = transaction.get(userStoreRef)
        val storeSnapshot = transaction.get(storeUserRef)

        val userPoints = userSnapshot.getLong("points")?.toInt() ?: 0
        val storePoints = storeSnapshot.getLong("points")?.toInt() ?: 0

        transaction.update(userStoreRef, "points", userPoints + delta)
        transaction.update(storeUserRef, "points", storePoints + delta)
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

