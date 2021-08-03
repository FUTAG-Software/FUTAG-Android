package com.futag.futag.model.etkinlik


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EtkinliklerModelItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("form_link")
    val formLink: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("details")
    val details: String
): Parcelable