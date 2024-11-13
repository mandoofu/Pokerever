package com.mandoo.pokerever.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                if (authResult.user != null) {
                    onResult(true)  // 로그인 성공
                } else {
                    onResult(false) // 로그인 실패
                }
            }
            .addOnFailureListener {
                onResult(false)  // 인증 실패
            }
    }
}
