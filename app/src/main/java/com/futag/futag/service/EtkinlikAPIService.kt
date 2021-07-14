package com.futag.futag.service

import com.futag.futag.model.etkinlik.EtkinliklerModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EtkinlikAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(EtkinlikAPI::class.java)

    fun etkinlikVerileriniGetir(): Single<EtkinliklerModel> {
        return api.getData()
    }

}