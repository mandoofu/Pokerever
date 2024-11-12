package com.mandoo.pokerever.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()

    // 기존 등록번호 중복 확인
    suspend fun isRegistrationNumberDuplicated(registrationNumber: String): Boolean {
        val query = firestore.collection("users")
            .whereEqualTo("registrationNumber", registrationNumber)
            .get()
            .await()

        return !query.isEmpty
    }

    // 닉네임 중복 확인
    suspend fun isNicknameDuplicated(nickname: String): Boolean {
        val query = firestore.collection("users")
            .whereEqualTo("nickname", nickname) // 'nickname' 필드로 검색
            .get()
            .await()

        return !query.isEmpty // 중복이 있으면 true
    }

    // 이메일 중복 확인
    suspend fun isEmailDuplicated(email: String): Boolean {
        val query = firestore.collection("users")
            .whereEqualTo("email", email) // 'email' 필드로 검색
            .get()
            .await()

        return !query.isEmpty // 중복이 있으면 true
    }
}
