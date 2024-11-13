package com.mandoo.pokerever.common

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

fun getStoresFromFirestore(onResult: (List<StoreInfo>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("stores") // "stores" 컬렉션에서 데이터를 가져옵니다
        .get()
        .addOnSuccessListener { result ->
            val stores = result.map { document ->
                document.toObject<StoreInfo>().copy(sid = document.id) // 문서 ID를 sid로 저장
            }
            onResult(stores)
        }
        .addOnFailureListener { exception ->
            // 오류 처리
            onResult(emptyList())
        }
}

fun addStoreToFirestore(storeInfo: StoreInfo) {
    val db = FirebaseFirestore.getInstance()
    val storeRef = db.collection("stores").document() // 새 문서 추가
    storeRef.set(storeInfo)
        .addOnSuccessListener {
            // 성공 처리
        }
        .addOnFailureListener { e ->
            // 실패 처리
        }
}
