package com.github.catomizer.di

import com.github.catomizer.catgallery.CatGalleryPresenter
import com.github.catomizer.network.CatApi
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class PresenterModule {

    @Provides
    fun provideCatGalleryPresenter(router: Router, catApi: CatApi): CatGalleryPresenter =
        CatGalleryPresenter(router, catApi)
}