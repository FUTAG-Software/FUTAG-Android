package com.futag.futag.model.advertising

import com.google.gson.annotations.SerializedName

data class AdsModelItem(
    @SerializedName("details")
    val details: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("redirecting_link")
    val redirectingLink: String,
    @SerializedName("title")
    val title: String
)