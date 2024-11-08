package com.example.app_didi_lanches.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.app_didi_lanches.helper.FirebaseHelper

@Parcelize
data class Product(
    var id: String = "",
    var name: String = "",
    var quantity: Double = 0.0,
    var measure: String = "",
    var category: String = ""
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
