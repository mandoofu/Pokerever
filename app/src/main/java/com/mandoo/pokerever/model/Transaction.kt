package com.mandoo.pokerever.model

data class Transaction(
    val id: String? = null,
    val from: String = "",
    val to: String = "",
    val fromType: String = "",
    val toType: String = "",
    val points: Int = 0,
    val timestamp: Long = 0L,
    var fromName: String? = null,
    var toName: String? = null
)

