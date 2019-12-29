package com.github.catomizer.catgallery.repository

import com.github.catomizer.error.NetworkErrorMapper
import com.github.catomizer.network.CatApi
import com.github.catomizer.network.model.CatApiModel
import io.reactivex.Single

class CatImagesRepository(
    private val catApi: CatApi,
    private val networkErrorHandler: NetworkErrorMapper
) {

    fun getCatImages(limit: Int, page: Int): Single<List<CatApiModel>> =
        catApi.getCatImages(limit, page)
            .onErrorResumeNext { Single.error(networkErrorHandler.mapError(it)) }
}