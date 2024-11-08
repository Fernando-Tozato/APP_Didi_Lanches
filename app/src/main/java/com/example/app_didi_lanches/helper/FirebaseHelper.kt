package com.example.app_didi_lanches.helper

import com.example.app_didi_lanches.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object{

        fun getDatabase() = FirebaseDatabase.getInstance().reference

        private fun getAuth() = FirebaseAuth.getInstance()

        fun getIdUser() = getAuth().uid

        fun isAuthenticated() = getAuth().currentUser != null

        fun validError(error: String) : Int {
            return when {
                error.contains("The supplied auth credential is incorrect, malformed or has expired") -> {
                    R.string.invalid_credentials
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.invalid_email
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use
                }
                error.contains("The given password is invalid") -> {
                    R.string.weak_password
                }
                else -> {
                    R.string.other_errors
                }
            }
        }

    }

}