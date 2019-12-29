package com.github.catomizer.catgallery.repository

import com.github.catomizer.data.db.FavoriteCatsDao
import com.github.catomizer.data.network.CatApi
import com.github.catomizer.data.network.model.CatApiModel
import com.github.catomizer.error.NetworkErrorMapper
import io.reactivex.Completable
import io.reactivex.Single

class CatImagesRepository(
    private val catApi: CatApi,
    private val favoriteCatsDao: FavoriteCatsDao,
    private val networkErrorHandler: NetworkErrorMapper
) {

    fun getCatImages(limit: Int, page: Int): Single<List<CatApiModel>> =
        catApi.getCatImages(limit, page)
            .onErrorResumeNext { Single.error(networkErrorHandler.mapError(it)) }

    fun addCatToFavorites(cats: List<CatApiModel>): Completable =
        favoriteCatsDao.insertCats(cats.map { it.toDbModel() })

    fun getAllFavorites(): Single<List<CatApiModel>> =
        favoriteCatsDao.getAll().map { catDbList ->
            catDbList.map { it.toApiModel() }
        }
}