package com.github.catomizer.data.network

import com.github.catomizer.data.network.model.CatApiModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/images/search")
    fun getCatImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int

    ): Single<List<CatApiModel>>
}