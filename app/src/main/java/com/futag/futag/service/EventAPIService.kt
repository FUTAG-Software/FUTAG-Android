package com.futag.futag.service

import com.futag.futag.model.event.EventsModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EventAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(EventAPI::class.java)

    fun getEventsData(): Single<EventsModel> {
        return api.getData()
    }

}