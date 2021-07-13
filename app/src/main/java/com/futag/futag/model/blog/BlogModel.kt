package com.futag.futag.model.blog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BlogModel(
    val resim: Int,
    val baslik: String,
    val konu: String,
    val yazar: String
): Parcelable