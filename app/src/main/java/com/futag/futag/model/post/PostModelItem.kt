package com.futag.futag.model.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PostModelItem(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("featured_image")
    val featuredImage: @RawValue FeaturedImage? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String
): Parcelable