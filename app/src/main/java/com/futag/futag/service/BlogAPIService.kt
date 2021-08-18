package com.futag.futag.service

import com.futag.futag.model.blog.BlogModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlogAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(BlogAPI::class.java)

    suspend fun getBlogData(): Response<BlogModel> {
        return api.getData()
    }

}