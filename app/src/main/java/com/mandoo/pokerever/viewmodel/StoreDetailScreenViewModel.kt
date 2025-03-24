package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mandoo.pokerever.model.Transaction

class StoreDetailScreenViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val transactionHistory = mutableStateOf<List<Transaction>>(emptyList())


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

    fun startObserving(storeId: String, userId: String) {
        observeUserPoints(storeId, userId)
        observeTransactionHistory(storeId)
    }

    fun observeUserPoints(storeId: String, userId: String) {
        db.collection("users").document(userId)
            .collection("addedStores").document(storeId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("StoreDetailVM", "Listen failed: ${e.localizedMessage}")
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    storeName.value = snapshot.getString("storeName") ?: "Unknown Store"
                    userPoints.value = snapshot.getLong("points")?.toInt() ?: 0
                }
            }
    }

    fun observeTransactionHistory(storeId: String) {
        db.collection("transactions")
            .whereEqualTo("to", storeId)
            .whereEqualTo("toType", "store")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("StoreDetailVM", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val documents = snapshots?.documents ?: return@addSnapshotListener
                val transactions = documents.mapNotNull { doc ->
                    val transaction = doc.toObject(Transaction::class.java)
                    transaction?.copy(id = doc.id) // doc.id 설정
                }

                // 모든 이름을 비동기로 가져와서 하나의 업데이트로 처리
                val updatedTransactions = mutableListOf<Transaction>()
                var updatedCount = 0
                val total = transactions.size * 2 // from + to

                if (transactions.isEmpty()) {
                    transactionHistory.value = emptyList()
                    return@addSnapshotListener
                }

                transactions.forEach { transaction ->
                    fetchName(transaction.from, transaction.fromType) { name ->
                        transaction.fromName = name
                        updatedCount++
                        if (updatedCount == total) {
                            transactionHistory.value = transactions.toList()
                        }
                    }
                    fetchName(transaction.to, transaction.toType) { name ->
                        transaction.toName = name
                        updatedCount++
                        if (updatedCount == total) {
                            transactionHistory.value = transactions.toList()
                        }
                    }
                }
            }
    }

    private fun fetchName(id: String, type: String, callback: (String) -> Unit) {
        val collection = if (type == "user") "users" else "stores"
        db.collection(collection).document(id).get()
            .addOnSuccessListener {
                val name = if (type == "user") it.getString("name") else it.getString("storeName")
                callback(name ?: "Unknown")
            }
            .addOnFailureListener {
                callback("Unknown")
            }
    }

}
