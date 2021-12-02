package com.futag.futag.data.network

import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.model.event.EventsModel
import com.futag.futag.model.post.PostModel
import retrofit2.Response
import retrofit2.http.GET

interface FlowAPI {

    // Ads api
    @GET("wp-json/iky/ads")
    suspend fun getAdsData(): Response<AdsModel>

    // Blog api
    @GET("wp-json/iky/blog")
    suspend fun getBlogData(): Response<BlogModel>

    // Event api
    @GET("wp-json/iky/etkinlikler")
    suspend fun getEventData(): Response<EventsModel>

    // Post api
    @GET("wp-json/iky/posts/")
    suspend fun getPostData(): Response<PostModel>

}