package com.github.catomizer.catgallery

import com.github.catomizer.base.BasePresenter
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.error.ErrorHandler
import com.github.catomizer.network.model.CatApiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router

@InjectViewState
class CatGalleryPresenter(
    private val router: Router,
    private val catImagesRepository: CatImagesRepository,
    private val errorHandler: ErrorHandler
) : BasePresenter<CatGalleryView>() {

    companion object {
        private const val DEFAULT_LIMIT = 100
    }

    private val catImages: MutableList<CatApiModel> = mutableListOf()
    private var page = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestCatImages()
    }

    fun requestCatImages() {
        viewState.setLoadingVisibility(true)
        catImagesRepository.getCatImages(DEFAULT_LIMIT, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.setLoadingVisibility(false)
                    catImages.addAll(it)
                    page++
                    viewState.showCatList(catImages)
                },
                {
                    viewState.setLoadingVisibility(false)
                    errorHandler.proceed(it) { errorResId -> viewState.showError(errorResId) }
                    viewState.showEmptyCatList()
                }
            )
            .apply { addDisposable(this) }
    }

}