package com.mandoo.pokerever.utils

object ValidationUtil {

    // 이름 유효성 검사
    fun isValidName(name: String): Boolean {
        return name.length >= 2 && name.matches(Regex("^[a-zA-Z가-힣]+$"))
    }

    // 주민등록번호 유효성 검사 (예: 6자리 숫자-1~4사이 숫자와 * 포함 6자리)
    fun isValidRegistrationNumber(frontNumber: String, backNumber: String): Boolean {
        // 입력된 뒷자리의 첫 숫자가 1~4 사이인지 확인
        if (backNumber.length < 1 || backNumber[0] !in '1'..'4') {
            return false
        }
        // 앞자리의 첫 숫자가 0이면 뒷자리 첫 숫자는 3 또는 4여야 함
        if (frontNumber[0] == '0' && backNumber[0] !in '3'..'4') {
            return false
        }
        // 앞자리의 첫 숫자가 1~9이면 뒷자리 첫 숫자는 1 또는 2여야 함
        if (frontNumber[0] in '1'..'9' && backNumber[0] !in '1'..'2') {
            return false
        }
        // 형식이 올바르면 통과
        return true
    }


    // 닉네임 유효성 검사 (2~6자, 한글/알파벳만 허용)
    fun isValidNickname(nickname: String): Boolean {
        return nickname.length in 2..6 && nickname.matches(Regex("^[a-zA-Z가-힣]+$"))
    }

    // 이메일 유효성 검사
    fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"))
    }

    // 비밀번호 유효성 검사 (예: 8자 이상, 영어,숫자,특수문자 포함)
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
