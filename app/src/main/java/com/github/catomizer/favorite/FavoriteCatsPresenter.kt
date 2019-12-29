package com.github.catomizer.favorite

import com.github.catomizer.base.BasePresenter
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.data.DownloadHelper
import com.github.catomizer.data.network.model.CatApiModel
import com.github.catomizer.error.ErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router

@InjectViewState
class FavoriteCatsPresenter(
    private val router: Router,
    private val catImagesRepository: CatImagesRepository,
    private val downloadHelper: DownloadHelper,
    private val errorHandler: ErrorHandler
) : BasePresenter<FavoriteCatsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestFavorites()
    }

    private fun requestFavorites() {
        catImagesRepository.getAllFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isNotEmpty()) {
                        viewState.showCatList(it)
                    } else {
                        viewState.showEmptyCatList()
                    }
                },
                {
                    errorHandler.proceed(it) { errorResId -> viewState.showMessage(errorResId) }
                }
            ).connect()
    }

    fun onBackPressed() = router.exit()

    fun downloadCatImages(catImages: List<CatApiModel>) {
        downloadHelper.downloadFiles(catImages.map { it.url })
    }
}