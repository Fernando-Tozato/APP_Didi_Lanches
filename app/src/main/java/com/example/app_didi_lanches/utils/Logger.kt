package com.example.app_didi_lanches.utils

object Logger {
    private fun getUserInfo(): String {
        val id = UserManager.userId ?: "Desconhecido"
        val name = UserManager.userName ?: "Desconhecido"
        return "UsuárioID: $id, Nome: $name"
    }

    fun d(message: String) {
        Timber.d("$message - ${getUserInfo()}")
    }

    fun i(message: String) {
        Timber.i("$message - ${getUserInfo()}")
    }
}