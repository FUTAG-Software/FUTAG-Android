package com.futag.futag.service

import com.futag.futag.model.blog.BlogModel
import io.reactivex.Single
import retrofit2.http.GET

interface BlogAPI {

    @GET("")
    fun getData(): Single<BlogModel>

}