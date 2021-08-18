package com.futag.futag.service

import com.futag.futag.model.post.PostModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(PostAPI::class.java)

    suspend fun getPosts(): Response<PostModel> {
        return api.getData()
    }

}