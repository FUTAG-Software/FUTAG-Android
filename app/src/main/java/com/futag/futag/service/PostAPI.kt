package com.futag.futag.service

import com.futag.futag.model.post.PostModel
import retrofit2.Response
import retrofit2.http.GET

interface PostAPI {

    @GET("wp-json/iky/posts/")
    suspend fun getData(): Response<PostModel>

}