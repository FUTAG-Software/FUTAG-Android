package com.futag.futag.model.etkinlik


import com.google.gson.annotations.SerializedName

data class EtkinliklerModelItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("title")
    val title: String
)