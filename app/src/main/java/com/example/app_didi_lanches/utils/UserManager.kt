package com.example.app_didi_lanches.utils

object UserManager {
    var userId: String? = null
    var userName: String? = null

    fun setUser(id: String, name: String) {
        userId = id
        userName = name
    }

    fun clearUser() {
        userId = null
        userName = null
    }
}