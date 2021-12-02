package com.futag.futag.data.repository

import com.futag.futag.data.network.FlowAPI
import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.model.post.PostModel
import com.futag.futag.util.Constants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeRepository {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(FlowAPI::class.java)

    // Ads
    suspend fun getAds(): Response<AdsModel> {
        return api.getAdsData()
    }

    // Post
    suspend fun getPosts(): Response<PostModel> {
        return api.getPostData()
    }

}