package com.github.catomizer.di

import com.github.catomizer.catgallery.CatGalleryPresenter
import com.github.catomizer.catgallery.repository.CatImagesRepository
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class PresenterModule {

    @Provides
    fun provideCatGalleryPresenter(router: Router, catImagesRepository: CatImagesRepository): CatGalleryPresenter =
        CatGalleryPresenter(router, catImagesRepository)
}