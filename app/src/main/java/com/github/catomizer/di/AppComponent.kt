package com.github.catomizer.di

import com.github.catomizer.AppActivity
import com.github.catomizer.catgallery.CatGalleryPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, NavigationModule::class, PresenterModule::class])
interface AppComponent {

    fun providePresenter(): CatGalleryPresenter

    fun inject(appActivity: AppActivity)
}