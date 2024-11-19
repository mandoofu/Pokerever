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

    var userInfoState: MutableState<UserInfoState> = mutableStateOf(UserInfoState())
    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    fun fetchUserAddedStores(userId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("addedStores")
            .get()
            .addOnSuccessListener { documents ->
                val stores = documents.map { doc ->
                    doc.toObject(StoreInfo::class.java).copy(sid = doc.id)
                }
                userAddedStores.value = stores
            }
            .addOnFailureListener { e ->
                Log.e("HomeViewModel", "Failed to fetch user added stores: $e")
            }
    }



    // 사용자가 추가한 매장 목록 상태
    val userAddedStores = mutableStateOf<List<StoreInfo>>(emptyList())
    // 로그인한 사용자 정보를 Firestore에서 가져오는 함수
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

                    // 유저 정보 업데이트
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

    // 비밀번호 변경 함수
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

    // 로그아웃 함수
    fun logout(onSuccess: () -> Unit) {
        // 비동기 작업을 처리할 때
        try {
            // 로그아웃 작업 예: Firebase 세션 종료
            FirebaseAuth.getInstance().signOut()
            // 로그아웃 성공 후 콜백 호출
            onSuccess()
        } catch (e: Exception) {
            // 오류 처리
            Log.e("LogoutError", "로그아웃 오류: ${e.message}")
            // 오류 발생 시 UI에 메시지 표시 등 처리
        }
    }
}

