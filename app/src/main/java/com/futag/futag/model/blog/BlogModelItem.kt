package com.futag.futag.model.blog

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BlogModelItem(
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