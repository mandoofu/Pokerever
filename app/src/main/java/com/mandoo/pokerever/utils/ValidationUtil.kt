package com.mandoo.pokerever.utils

object ValidationUtil {

    // 주민등록번호 유효성 검사 (예: 6자리 숫자-1~4사이 숫자와 * 포함 6자리)
    fun isValidRegistrationNumber(registrationNumber: String): Boolean {
        return registrationNumber.matches(Regex("^[0-9]{6}-(0[0-9]{5}[3-4][*]{6})$"))
    }

    // 닉네임 유효성 검사 (예: 2~6자 길이, 한글, 알파벳, 숫자만 허용)
    fun isValidNickname(nickname: String): Boolean {
        return nickname.length in 2..6 && nickname.matches(Regex("^[a-zA-Z0-9가-힣]+$"))
    }

    // 이메일 유효성 검사
    fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"))
    }

    // 비밀번호 유효성 검사 (예: 8자 이상, 숫자와 특수문자 포함)
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 &&
                password.matches(Regex(".*[A-Za-z].*")) &&  // 영어 대소문자 포함
                password.matches(Regex(".*[0-9].*")) &&    // 숫자 포함
                password.matches(Regex(".*[!@#\$%^&*].*")) // 특수문자 포함
    }

    // 비밀번호 확인 유효성 검사 (비밀번호와 동일한지 확인)
    fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}
