package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // 이메일 중복 체크 상태
    var isEmailDuplicate: Boolean? = null
    var isDuplicate: Boolean? = null

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
                // 이메일로 Firebase Authentication을 사용하여 회원가입 진행
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

                db.collection("users") // "users" 컬렉션에 저장
                    .document(userCredential.user?.uid ?: "")
                    .set(user)
                    .addOnSuccessListener {
                        Log.d("RegisterViewModel", "User successfully registered")
                    }
                    .addOnFailureListener { e ->
                        Log.w("RegisterViewModel", "Error adding user", e)
                    }

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Registration failed: ${e.message}")
            }
        }
    }

    // 이메일 중복 체크 함수
    fun checkDuplicateEmail(email: String) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                isEmailDuplicate = !result.isEmpty
            }
    }

    // 닉네임 중복 체크 함수
    fun checkDuplicateNickname(nickname: String) {
        db.collection("users")
            .whereEqualTo("nickname", nickname)
            .get()
            .addOnSuccessListener { result ->
                isDuplicate = !result.isEmpty
            }
    }
}
