package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // 이메일 및 닉네임 중복 체크 상태
    var isEmailDuplicate = mutableStateOf<Boolean?>(null)
        private set

    var isDuplicate = mutableStateOf<Boolean?>(null)
        private set

    // 유저 등록 함수
    fun registerUser(
        name: String,
        frontNumber: String,
        backNumber: String,
        email: String,
        password: String,
        nickname: String
    ) {
        viewModelScope.launch {
            try {
                // Firebase Authentication으로 회원가입
                val userCredential = auth.createUserWithEmailAndPassword(email, password).await()

                // Firestore에 사용자 정보 저장
                val user = hashMapOf(
                    "name" to name,
                    "frontNumber" to frontNumber,
                    "backNumber" to backNumber,
                    "email" to email,
                    "nickname" to nickname,
                    "uid" to userCredential.user?.uid
                )

                db.collection("users")
                    .document(userCredential.user?.uid ?: "")
                    .set(user)
                    .addOnSuccessListener {
                        Log.d("Firestore", "User data successfully written to Firestore")

                        // 회원가입 성공 처리
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error writing user data to Firestore: ${e.localizedMessage}")
                        // 에러 처리
                    }
            } catch (e: Exception) {
                // 예외 처리
            }
        }
    }

    // 이메일 중복 확인 함수
    fun checkDuplicateEmail(email: String) {
        viewModelScope.launch {
            try {
                val query = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()
                isEmailDuplicate.value = !query.isEmpty
            } catch (e: Exception) {
                isEmailDuplicate.value = null
            }
        }
    }

    // 닉네임 중복 확인 함수
    fun checkDuplicateNickname(nickname: String) {
        viewModelScope.launch {
            try {
                val query = db.collection("users")
                    .whereEqualTo("nickname", nickname)
                    .get()
                    .await()
                isDuplicate.value = !query.isEmpty
            } catch (e: Exception) {
                isDuplicate.value = null
            }
        }
    }
}
