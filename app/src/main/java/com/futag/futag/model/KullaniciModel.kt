package com.futag.futag.model

import com.google.firebase.Timestamp

data class KullaniciModel(
    var isim: String,
    var soyisim: String,
    val email: String,
    val uid: String,
    var dogumGunu: String,
    var profilResmi: String?,
    var profilResmiAdi: String?,
    val kayitTarihi: Timestamp
)