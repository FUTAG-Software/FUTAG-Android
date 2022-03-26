package com.futag.futag.data.network

import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.model.event.EventsModel
import com.futag.futag.model.post.PostModel
import com.futag.futag.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlowApiImpl : FlowAPI {

    private val api = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(FlowAPI::class.java)

    override suspend fun getAdsData(): Response<AdsModel> {
        return api.getAdsData()
    }

    override suspend fun getBlogData(): Response<BlogModel> {
        return api.getBlogData()
    }

    override suspend fun getEventData(): Response<EventsModel> {
        return api.getEventData()
    }

    override suspend fun getPostData(): Response<PostModel> {
        return api.getPostData()
    }
}