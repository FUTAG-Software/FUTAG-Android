package com.futag.futag.service

import com.futag.futag.model.anasayfa.AnaSayfaModel
import io.reactivex.Single
import retrofit2.http.GET

interface AnaSayfaAPI {

    @GET("")
    fun getData(): Single<AnaSayfaModel>

}