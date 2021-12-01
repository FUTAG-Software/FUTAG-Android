package com.futag.futag.data.repository

import com.futag.futag.data.api.FlowAPI
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlogRepository {

    private val api = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(FlowAPI::class.java)

    suspend fun getBlogData(): Response<BlogModel> {
        return api.getBlogData()
    }

}