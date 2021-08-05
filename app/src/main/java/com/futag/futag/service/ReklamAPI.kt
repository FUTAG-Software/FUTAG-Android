package com.futag.futag.service

import com.futag.futag.model.reklam.ReklamlarModel
import io.reactivex.Single
import retrofit2.http.GET

interface ReklamAPI {

    @GET("")
    fun getData(): Single<ReklamlarModel>

}