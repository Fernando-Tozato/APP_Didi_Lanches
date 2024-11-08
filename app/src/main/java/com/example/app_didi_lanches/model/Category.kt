package com.example.app_didi_lanches.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.app_didi_lanches.helper.FirebaseHelper

@Parcelize
data class Category(
    var id: String = "",
    var name: String = "",
    var color: Int = 0
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
