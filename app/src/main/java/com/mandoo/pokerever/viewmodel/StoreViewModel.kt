package com.mandoo.pokerever.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mandoo.pokerever.common.StoreInfo
import com.mandoo.pokerever.common.getStoresFromFirestore
import kotlinx.coroutines.launch

class StoreViewModel : ViewModel() {
    var storeList = mutableStateOf<List<StoreInfo>>(emptyList())
    var imageUrl = mutableStateOf<String>("")  // 이미지 URL 상태 추가

    // Firebase Storage에서 이미지 URL 가져오기
    fun loadImage(imagePath: String) {
        val storage = Firebase.storage
        val storageReference = storage.reference.child(imagePath)

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // 이미지 URL을 상태로 저장
            imageUrl.value = uri.toString()
            Log.d("StoreViewModel", "Image URL loaded: $imageUrl")
        }.addOnFailureListener {
            // 오류 처리
            imageUrl.value = ""  // 오류 발생 시 빈 문자열로 설정
            Log.e("StoreViewModel", "Failed to load image URL", it)
        }
    }

    // Firestore에서 매장 정보 가져오기
    fun loadStores() {
        viewModelScope.launch {
            Log.d("StoreViewModel", "Attempting to load stores from Firestore")
            getStoresFromFirestore { stores ->
                if (stores.isNotEmpty()) {
                    Log.d("StoreViewModel", "Successfully loaded ${stores.size} stores")
                    storeList.value = stores

                    // 각 매장의 이미지 URL을 로드 (imageRes 필드를 사용)
                    stores.forEach { store ->
                        loadImage(store.imageRes)  // 각 매장에 대해 이미지를 로드
                    }
                } else {
                    Log.d("StoreViewModel", "No stores found")
                    storeList.value = emptyList()
                }
            }
        }
    }
}
