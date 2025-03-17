package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.utils.fetchUserAddedStores
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.*

class StoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var storeList by mutableStateOf<List<StoreInfo>>(emptyList())
    var userAddedStores = mutableStateListOf<String>() // 유저가 추가한 매장 ID

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    // Firestore에서 모든 매장 로드
    fun loadStores(userLat: Double, userLng: Double) {
        viewModelScope.launch {
            db.collection("stores").get().addOnSuccessListener { result ->
                val stores = result.documents.map { doc ->
                    doc.toObject(StoreInfo::class.java)?.copy(
                        sid = doc.id,
                        distance = calculateDistance(
                            userLat,
                            userLng,
                            doc.getDouble("latitude") ?: 0.0,
                            doc.getDouble("longitude") ?: 0.0
                        )
                    )
                }.filterNotNull()
                    .sortedBy { it.distance } // 거리 순 정렬
                storeList = stores
                Log.d("StoreViewModel", "Firestore 요청 성공: ${stores.size}개의 매장 데이터 로드 (거리순 정렬됨)")
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
        val R = 6371 // 지구 반지름 (km)
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distanceKm = R * c // 거리 (km 단위)

        return distanceKm * 1000 // m 단위 변환
    }

    // Firestore에서 모든 매장 로드 및 거리 계산
    fun loadStoresWithDistance(userLat: Double, userLon: Double) {
        viewModelScope.launch {
            db.collection("stores").get().addOnSuccessListener { result ->
                val stores = result.documents.mapNotNull { doc ->
                    val geoPoint = doc.getGeoPoint("geoPoint") // Firestore에서 GeoPoint 가져오기

                    geoPoint?.let {
                        doc.toObject(StoreInfo::class.java)?.copy(
                            sid = doc.id,
                            distance = calculateDistance(
                                userLat, userLon, it.latitude, it.longitude // GeoPoint 사용
                            )
                        )
                    }
                }
                    .sortedBy { it.distance } // 거리순 정렬 적용
                storeList = stores
                Log.d("StoreViewModel", "매장 ${stores.size}개 로드 (거리순 정렬 완료)")
            }
        }
    }

    // 데이터 초기화
    fun initializeData(userLat: Double, userLon: Double) {
        val userId = getUserId()
        if (userId != null) {
            viewModelScope.launch {
                val stores = fetchStores(userLat, userLon) // Firestore에서 매장 데이터 가져오기
                val addedStores = fetchUserAddedStores(userId) // 유저가 추가한 매장 데이터 가져오기

                storeList = stores
                userAddedStores.clear()
                userAddedStores.addAll(addedStores)
            }
        } else {
            Log.w("StoreViewModel", "사용자 ID를 가져올 수 없습니다.")
        }
    }

    private suspend fun fetchStores(userLat: Double, userLon: Double): List<StoreInfo> {
        return try {
            val result = db.collection("stores").get().await()
            val stores = result.documents.mapNotNull { doc ->
                val geoPoint = doc.getGeoPoint("geoPoint")
                geoPoint?.let {
                    StoreInfo(
                        sid = doc.id,
                        storeName = doc.getString("storeName") ?: "이름 없음",
                        address = doc.getString("address") ?: "주소 없음",
                        imageRes = doc.getString("imageRes") ?: "",
                        distance = calculateDistance(userLat, userLon, it.latitude, it.longitude),
                        geoPoint = geoPoint
                    )
                }
            }.sortedBy { it.distance }

            Log.d("StoreViewModel", "Firestore에서 ${stores.size}개 매장 로드 완료 (거리순 정렬 적용됨)")
            stores
        } catch (e: Exception) {
            Log.e("StoreViewModel", "Firestore 데이터 로드 실패: ${e.localizedMessage}")
            emptyList()
        }
    }

    private suspend fun fetchUserAddedStores(userId: String): List<String> {
        return try {
            val result = db.collection("users").document(userId).collection("addedStores").get().await()
            val addedStoreIds = result.documents.map { it.id }
            Log.d("StoreViewModel", "유저가 추가한 매장 로드 성공: ${addedStoreIds.size}개")
            addedStoreIds
        } catch (e: Exception) {
            Log.e("StoreViewModel", "Failed to fetch added stores: ${e.localizedMessage}")
            emptyList()
        }
    }

    // 유저가 매장을 추가
    fun addStoreForUser(userId: String, storeInfo: StoreInfo, onSuccess: () -> Unit) {
        val userStoreRef = db.collection("users").document(userId).collection("addedStores")
            .document(storeInfo.sid)
        val storeUserRef = db.collection("stores").document(storeInfo.sid).collection("users")
            .document(userId)

        db.runBatch { batch ->
            batch.set(
                userStoreRef, mapOf(
                    "storeName" to storeInfo.storeName,
                    "address" to storeInfo.address,
                    "points" to 0,
                    "imageRes" to storeInfo.imageRes
                )
            )
            batch.set(
                storeUserRef, mapOf(
                    "userName" to "사용자 이름", // 적절한 값으로 변경 필요
                    "points" to 0
                )
            )
        }.addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                Log.e("StoreViewModel", "Failed to add store: ${e.localizedMessage}")
            }
    }
}
