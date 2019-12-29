package com.github.catomizer.di

import com.github.catomizer.DownloadHelper
import com.github.catomizer.catgallery.CatGalleryPresenter
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.error.ErrorHandler
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class PresenterModule {

    @Provides
    fun provideCatGalleryPresenter(
        router: Router,
        catImagesRepository: CatImagesRepository,
        downloadHelper: DownloadHelper,
        errorHandler: ErrorHandler
    ): CatGalleryPresenter =
        CatGalleryPresenter(router, catImagesRepository, downloadHelper, errorHandler)
}