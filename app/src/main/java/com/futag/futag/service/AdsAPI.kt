package com.futag.futag.service

import com.futag.futag.model.advertising.AdsModel
import io.reactivex.Single
import retrofit2.http.GET

interface AdsAPI {

    @GET("wp-json/iky/ads")
    fun getData(): Single<AdsModel>

}