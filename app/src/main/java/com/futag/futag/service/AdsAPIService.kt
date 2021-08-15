package com.futag.futag.service

import com.futag.futag.model.advertising.AdsModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AdsAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(AdsAPI::class.java)

    fun getAds(): Single<AdsModel> {
        return api.getData()
    }

}