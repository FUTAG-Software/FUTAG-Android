package com.futag.futag.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GonderiModel(
    val resim: Int,
    val baslik: String,
    val detay: String,
    val yazar: String
): Parcelable
