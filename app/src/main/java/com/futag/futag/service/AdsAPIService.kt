package com.futag.futag.service

import com.futag.futag.model.advertising.AdsModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdsAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(AdsAPI::class.java)

    suspend fun getAds(): Response<AdsModel> {
        return api.getData()
    }

}