package com.mandoo.pokerever.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.mandoo.pokerever.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionList(transactions: List<Transaction>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        items(transactions) { transaction ->
            TransactionItem(transaction)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}
fun fetchNameFromFirestore(id: String, type: String, onResult: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val collection = if (type == "user") "users" else "stores"

    db.collection(collection).document(id)
        .get()
        .addOnSuccessListener { document ->
            // `stores`에서는 `storeName`, `users`에서는 `name` 필드를 가져오도록 설정
            val name = if (type == "user") {
                document.getString("name") ?: "Unknown User"
            } else {
                document.getString("storeName") ?: "Unknown Store"
            }
            onResult(name)
        }
        .addOnFailureListener {
            onResult("Unknown") // 실패 시 기본값
        }
}


@Composable
fun TransactionItem(transaction: Transaction) {
    val dateTime = formatTimestamp(transaction.timestamp)

    // Firestore에서 이름 가져오기
    val fromName = remember { mutableStateOf(transaction.from) } // 기본값은 ID
    val toName = remember { mutableStateOf(transaction.to) }     // 기본값은 ID

    LaunchedEffect(transaction.from) {
        fetchNameFromFirestore(transaction.from, transaction.fromType) { name ->
            fromName.value = name
        }
    }
    LaunchedEffect(transaction.to) {
        fetchNameFromFirestore(transaction.to, transaction.toType) { name ->
            toName.value = name
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = fromName.value, fontSize = 14.sp, color = Color.White)
        Text(text = toName.value, fontSize = 14.sp, color = Color.White)
        Text(text = dateTime, fontSize = 12.sp, color = Color.White)
        Text(text = "${transaction.points}P", fontSize = 14.sp, color = Color.White)
    }
}


fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(date)
}
