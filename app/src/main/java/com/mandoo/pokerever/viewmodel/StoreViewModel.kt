package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.common.getStoresFromFirestore
import com.mandoo.pokerever.utils.addStoreToUser
import com.mandoo.pokerever.utils.initializeUserStorePoints
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
            getStoresFromFirestore { stores ->
                storeList.value = stores
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
    }

    // 유저가 매장을 추가
    fun addStoreForUser(userId: String, storeInfo: StoreInfo) {
        viewModelScope.launch {
            val storeRef = db.collection("stores").document(storeInfo.sid)

            storeRef.set(storeInfo)
                .addOnSuccessListener {
                    addStoreToUser(
                        userId = userId,
                        storeId = storeInfo.sid,
                        onSuccess = {
                            initializeUserStorePoints(
                                userId = userId,
                                storeId = storeInfo.sid,
                                onSuccess = {
                                    // 데이터 갱신
                                    loadUserAddedStores(userId) // 추가된 매장을 다시 로드
                                    loadStores() // LazyStoreList UI 갱신
                                },
                                onFailure = { error ->
                                    Log.e("StoreViewModel", "Failed to initialize points: $error")
                                }
                            )
                        },
                        onFailure = { error ->
                            Log.e("StoreViewModel", "Failed to add store to user: $error")
                        }
                    )
                }
                .addOnFailureListener { e ->
                    Log.e("StoreViewModel", "Failed to add store: ${e.localizedMessage}")
                }
        }
    }


    // 모든 데이터를 초기화
    fun initializeData() {
        val userId = auth.currentUser?.uid ?: return
        loadStores()
        loadUserAddedStores(userId)
    }
}

