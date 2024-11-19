package com.mandoo.pokerever.model

data class Transaction(
    val from: String = "",       // 보낸 사람 ID
    val fromType: String = "",   // 보낸 사람 유형 ("user" 또는 "store")
    val to: String = "",         // 받는 사람 ID
    val toType: String = "",     // 받는 사람 유형 ("user" 또는 "store")
    val timestamp: Long = 0L,    // 전송 시간
    val points: Int = 0          // 전송 포인트
)

