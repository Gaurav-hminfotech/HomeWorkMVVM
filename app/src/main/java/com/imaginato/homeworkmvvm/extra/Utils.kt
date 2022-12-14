package com.imaginato.homeworkmvvm.extra

object Utils {
    fun isPasswordValid(password: String): Boolean {
        return password.length in 6..15
    }

    fun isUserNameValid(username: String): Boolean {
        return !username.isNullOrBlank() && (username.length>0 && username.length<30)
    }
}