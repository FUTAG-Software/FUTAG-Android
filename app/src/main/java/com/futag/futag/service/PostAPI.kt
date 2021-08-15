package com.futag.futag.service

import com.futag.futag.model.mainscreen.MainScreenModel
import io.reactivex.Single
import retrofit2.http.GET

interface PostAPI {

    @GET("wp-json/iky/posts/")
    fun getData(): Single<MainScreenModel>

}