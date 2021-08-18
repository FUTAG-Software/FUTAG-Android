package com.futag.futag.service

import com.futag.futag.model.advertising.AdsModel
import retrofit2.Response
import retrofit2.http.GET

interface AdsAPI {

    @GET("wp-json/iky/ads")
    suspend fun getData(): Response<AdsModel>

}