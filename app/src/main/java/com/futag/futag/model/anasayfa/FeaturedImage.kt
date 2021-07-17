package com.futag.futag.model.anasayfa

import com.google.gson.annotations.SerializedName

data class FeaturedImage(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)