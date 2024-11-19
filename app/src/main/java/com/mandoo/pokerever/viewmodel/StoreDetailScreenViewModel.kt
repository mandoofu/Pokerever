package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class StoreDetailScreenViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    val storeName = mutableStateOf("")
    val userPoints = mutableStateOf(0)

    // 매장 및 사용자 정보 로드
    fun loadStoreAndUserPoints(storeId: String, userId: String) {
        // `users/{userId}/addedStores/{storeId}`에서 데이터 로드
        db.collection("users").document(userId)
            .collection("addedStores").document(storeId)
            .get()
            .addOnSuccessListener { document ->
                storeName.value = document.getString("storeName") ?: "Unknown Store"
                userPoints.value = document.getLong("points")?.toInt() ?: 0
            }
            .addOnFailureListener { e ->
                Log.e("StoreDetailVM", "Failed to fetch user points: ${e.localizedMessage}")
            }
    }
}