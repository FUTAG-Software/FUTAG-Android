package com.futag.futag.service

import com.futag.futag.model.etkinlik.EtkinliklerModel
import io.reactivex.Single
import retrofit2.http.GET

interface EtkinlikAPI {

    @GET("")
    fun getData(): Single<EtkinliklerModel>

}