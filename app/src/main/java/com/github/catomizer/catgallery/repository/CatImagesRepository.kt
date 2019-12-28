package com.github.catomizer.catgallery.repository

import com.github.catomizer.error.NetworkErrorHandler
import com.github.catomizer.network.CatApi
import com.github.catomizer.network.model.CatApiModel
import io.reactivex.Single

class CatImagesRepository(
    private val catApi: CatApi,
    private val networkErrorHandler: NetworkErrorHandler
) {

    fun getCatImages(limit: Int, page: Int): Single<List<CatApiModel>> =
        catApi.getCatImages(limit, page)
            .onErrorResumeNext { networkErrorHandler.handleError(it) }
}