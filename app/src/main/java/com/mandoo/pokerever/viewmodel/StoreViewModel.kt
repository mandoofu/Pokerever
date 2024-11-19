package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mandoo.pokerever.common.StoreInfo
import kotlinx.coroutines.launch
import kotlin.math.*

class StoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var storeList = mutableStateListOf<StoreInfo>() // 모든 매장
    var userAddedStores = mutableStateListOf<String>() // 유저가 추가한 매장 ID

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    // Firestore에서 모든 매장 로드
    fun loadStores() {
        viewModelScope.launch {
            db.collection("stores").get().addOnSuccessListener { result ->
                val stores = result.documents.map { doc ->
                    doc.toObject(StoreInfo::class.java)?.copy(sid = doc.id)
                }.filterNotNull() // null 필터링
                storeList.clear()
                storeList.addAll(stores)
                Log.d("StoreViewModel", "Firestore 요청 성공: ${stores.size}개의 매장 데이터 로드")
            }.addOnFailureListener { e ->
                Log.e("StoreViewModel", "Failed to load stores: ${e.localizedMessage}")
            }
        }
    }

    // 유저가 추가한 매장 로드
    fun loadUserAddedStores(userId: String) {
        viewModelScope.launch {
            db.collection("users").document(userId).collection("addedStores")
                .get()
                .addOnSuccessListener { result ->
                    val addedStoreIds = result.documents.map { it.id }
                    userAddedStores.clear()
                    userAddedStores.addAll(addedStoreIds)
                    Log.d("StoreViewModel", "유저가 추가한 매장 로드 성공: ${addedStoreIds.size}개")
                }.addOnFailureListener { e ->
                    Log.e("StoreViewModel", "Failed to fetch added stores: ${e.localizedMessage}")
                }
        }
    }

    // 거리 계산 함수
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val radius = 6371 // 지구 반지름 (km)
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return radius * c * 1000 // 거리(m)
    }

    // Firestore에서 모든 매장 로드 및 거리 계산
    fun loadStoresWithDistance(userLat: Double, userLon: Double) {
        viewModelScope.launch {
            db.collection("stores").get().addOnSuccessListener { result ->
                val stores = result.documents.mapNotNull { doc ->
                    doc.toObject(StoreInfo::class.java)?.copy(sid = doc.id)?.let { store ->
                        val distance = store.geoPoint?.let {
                            calculateDistance(userLat, userLon, it.latitude, it.longitude)
                        } ?: Double.MAX_VALUE
                        store.copy(distance = distance)
                    }
                }
                storeList.clear()
                storeList.addAll(stores.sortedBy { it.distance })
                Log.d("StoreViewModel", "거리 계산 후 매장 리스트 정렬 완료")
            }.addOnFailureListener { e ->
                Log.e("StoreViewModel", "Failed to load stores: ${e.localizedMessage}")
            }
        }
    }

    // 데이터 초기화
    fun initializeData(userLat: Double, userLon: Double) {
        val userId = getUserId()
        if (userId != null) {
            loadStoresWithDistance(userLat, userLon)
            loadUserAddedStores(userId)
        } else {
            Log.w("StoreViewModel", "사용자 ID를 가져올 수 없습니다.")
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
}
