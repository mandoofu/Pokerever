package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mandoo.pokerever.common.StoreInfo
import kotlinx.coroutines.launch

class StoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var storeList = mutableStateOf<List<StoreInfo>>(emptyList()) // 모든 매장
    var userAddedStores = mutableStateOf<List<String>>(emptyList()) // 유저가 추가한 매장 ID

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    // 모든 매장 로드
    fun loadStores() {
        viewModelScope.launch {
            db.collection("stores").get().addOnSuccessListener { result ->
                storeList.value = result.map { doc ->
                    doc.toObject(StoreInfo::class.java).copy(sid = doc.id)
                }
            }.addOnFailureListener { e ->
                Log.e("StoreViewModel", "Failed to load stores: ${e.localizedMessage}")
            }
        }
    }

    // 유저가 추가한 매장 로드
    fun loadUserAddedStores(userId: String) {
        db.collection("users").document(userId).collection("addedStores")
            .get()
            .addOnSuccessListener { result ->
                val addedStoreIds = result.documents.map { it.id }
                userAddedStores.value = addedStoreIds
            }
            .addOnFailureListener { e ->
                Log.e("StoreViewModel", "Failed to fetch added stores: ${e.localizedMessage}")
            }
    }

    // 유저가 매장을 추가
    fun addStoreForUser(userId: String, storeInfo: StoreInfo, onSuccess: () -> Unit) {
        val userStoreRef = db.collection("users").document(userId).collection("addedStores")
            .document(storeInfo.sid)
        val storeUserRef = db.collection("stores").document(storeInfo.sid).collection("users")
            .document(userId)

        db.runBatch { batch ->
            batch.set(userStoreRef, mapOf(
                "storeName" to storeInfo.storeName,
                "address" to storeInfo.address,
                "points" to 0,
                "imageRes" to storeInfo.imageRes
            ))
            batch.set(storeUserRef, mapOf(
                "userName" to "사용자 이름", // 적절한 값으로 변경 필요
                "points" to 0
            ))
        }.addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                Log.e("StoreViewModel", "Failed to add store: ${e.localizedMessage}")
            }
    }

    // 데이터 초기화
    fun initializeData() {
        val userId = getUserId() ?: return
        loadStores()
        loadUserAddedStores(userId)
    }
}
