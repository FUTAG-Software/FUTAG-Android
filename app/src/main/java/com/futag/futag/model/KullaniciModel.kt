package com.futag.futag.model

import com.google.firebase.Timestamp

data class KullaniciModel(
    val isim: String,
    val soyisim: String,
    val email: String,
    val uid: String,
    val dogumGunu: String,
    val profilResmi: String?,
    val kayitTarihi: Timestamp
)