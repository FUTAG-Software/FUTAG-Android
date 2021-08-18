package com.futag.futag.service

import com.futag.futag.model.event.EventsModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventAPIService {

    private val BASE_URL = "https://www.futag.net/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(EventAPI::class.java)

    suspend fun getEventsData(): Response<EventsModel> {
        return api.getData()
    }

}