package com.futag.futag.service

import com.futag.futag.model.event.EventsModel
import retrofit2.Response
import retrofit2.http.GET

interface EventAPI {

    @GET("wp-json/iky/etkinlikler")
    suspend fun getData(): Response<EventsModel>

}