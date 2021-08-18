package com.futag.futag.service

import com.futag.futag.model.blog.BlogModel
import retrofit2.Response
import retrofit2.http.GET

interface BlogAPI {

    @GET("wp-json/iky/blog")
    suspend fun getData(): Response<BlogModel>

}