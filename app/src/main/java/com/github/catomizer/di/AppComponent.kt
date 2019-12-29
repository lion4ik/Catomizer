package com.github.catomizer.di

import com.github.catomizer.AppActivity
import com.github.catomizer.catgallery.CatGalleryPresenter
import com.github.catomizer.favorite.FavoriteCatsPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class, NavigationModule::class, PresenterModule::class])
interface AppComponent {

    fun provideCatGalleryPresenter(): CatGalleryPresenter

    fun provideFavoriteCatsPresenter(): FavoriteCatsPresenter

    fun inject(appActivity: AppActivity)
}