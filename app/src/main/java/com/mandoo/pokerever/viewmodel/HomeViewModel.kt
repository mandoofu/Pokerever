package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.model.UserInfoState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // 사용자 정보 상태
    var userInfoState: MutableState<UserInfoState> = mutableStateOf(UserInfoState())

    // 사용자가 추가한 매장 목록 상태
    val userAddedStores: MutableState<List<StoreInfo>> = mutableStateOf(emptyList())

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    // 사용자가 추가한 매장 정보를 Firestore에서 가져오는 함수
    fun fetchUserAddedStores(userId: String) {
        viewModelScope.launch {
            try {
                val documents = firestore.collection("users")
                    .document(userId)
                    .collection("addedStores")
                    .get()
                    .await()

                // Firestore에서 데이터 매핑
                val stores = documents.map { doc ->
                    StoreInfo(
                        sid = doc.id,
                        storeName = doc.getString("storeName") ?: "",
                        address = doc.getString("address") ?: "",
                        points = doc.getLong("points")?.toInt() ?: 0,
                        imageRes = doc.getString("imageRes") ?: ""
                    )
                }

                userAddedStores.value = stores // 상태 업데이트
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to fetch user added stores: ${e.localizedMessage}")
            }
        }
    }

    // Firestore에서 사용자 정보 가져오기
    fun fetchUserInfo() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userInfoState.value = UserInfoState(isLoading = true) // 로딩 상태로 변경
            viewModelScope.launch {
                try {
                    val document = firestore.collection("users").document(userId).get().await()
                    val name = document.getString("name")
                    val nickname = document.getString("nickname")
                    val frontNumber = document.getString("frontNumber")

                    // 사용자 정보 업데이트
                    userInfoState.value = UserInfoState(
                        name = name,
                        nickname = nickname,
                        frontNumber = frontNumber,
                        isLoading = false
                    )
                } catch (e: Exception) {
                    // 데이터 불러오기 실패 시 처리
                    userInfoState.value =
                        UserInfoState(isLoading = false, errorMessage = "정보를 가져오는 데 실패했습니다.")
                }
            }
        }
    }

    // 비밀번호 변경
    fun updatePassword(newPassword: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val user = auth.currentUser
        user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure(task.exception?.localizedMessage ?: "비밀번호 변경 실패")
            }
        }
    }

    // 로그아웃 처리
    fun logout(onSuccess: () -> Unit) {
        try {
            FirebaseAuth.getInstance().signOut()
            onSuccess()
        } catch (e: Exception) {
            Log.e("LogoutError", "로그아웃 오류: ${e.message}")
        }
    }
}
