package com.github.catomizer.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/images/search")
    fun getCatImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    )
}