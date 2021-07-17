package com.futag.futag.service

import com.futag.futag.model.anasayfa.AnaSayfaModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnaSayfaAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(AnaSayfaAPI::class.java)

    fun anaSayfaVerileriniGetir(): Single<AnaSayfaModel>{
        return api.getData()
    }

}