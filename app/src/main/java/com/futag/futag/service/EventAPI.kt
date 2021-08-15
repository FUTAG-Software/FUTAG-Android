package com.futag.futag.service

import com.futag.futag.model.event.EventsModel
import io.reactivex.Single
import retrofit2.http.GET

interface EventAPI {

    @GET("wp-json/iky/etkinlikler")
    fun getData(): Single<EventsModel>

}