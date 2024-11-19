package com.mandoo.pokerever.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mandoo.pokerever.model.Transaction

fun fetchTransactionHistory(
    userId: String,
    storeId: String,
    onResult: (List<Transaction>) -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    db.collection("transactions")
        .whereIn("from", listOf(userId, storeId))
        .whereIn("to", listOf(userId, storeId))
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { snapshot ->
            val transactions = snapshot.documents.mapNotNull { it.toObject(Transaction::class.java) }
            onResult(transactions)
        }
        .addOnFailureListener { e ->
            onFailure(e.localizedMessage ?: "Failed to fetch transactions")
        }
}




