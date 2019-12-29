package com.github.catomizer.catgallery

import com.github.catomizer.R
import com.github.catomizer.base.BasePresenter
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.data.DownloadHelper
import com.github.catomizer.data.network.model.CatApiModel
import com.github.catomizer.error.ErrorHandler
import com.github.catomizer.navigation.FavoriteCatsScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router

@InjectViewState
class CatGalleryPresenter(
    private val router: Router,
    private val catImagesRepository: CatImagesRepository,
    private val downloadHelper: DownloadHelper,
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
                    if (catImages.isNotEmpty()) {
                        viewState.showCatList(catImages)
                    } else {
                        viewState.showEmptyCatList()
                    }
                },
                {
                    viewState.setLoadingVisibility(false)
                    errorHandler.proceed(it) { errorResId -> viewState.showMessage(errorResId) }
                    viewState.showEmptyCatList()
                }
            )
            .apply { addDisposable(this) }
    }

    fun downloadCatImages(catImages: List<CatApiModel>) {
        downloadHelper.downloadFiles(catImages.map { it.url })
    }

    fun onFavoritesClicked() {
        router.navigateTo(FavoriteCatsScreen())
    }

    fun onAddToFavoriteClicked(catImages: List<CatApiModel>) {
        catImagesRepository.addCatToFavorites(catImages)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { viewState.showMessage(R.string.cat_gallery_add_favorite_success) },
                { viewState.showMessage(R.string.cat_gallery_add_favorite_error) }
            )
            .apply { addDisposable(this) }
    }
}