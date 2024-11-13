// UserInfoState.kt
package com.mandoo.pokerever.model

data class UserInfoState(
    val name: String? = null,
    val nickname: String? = null,
    val frontNumber: String? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
