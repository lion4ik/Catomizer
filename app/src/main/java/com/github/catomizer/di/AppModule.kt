package com.github.catomizer.di

import android.content.Context
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.error.NetworkErrorHandler
import com.github.catomizer.network.CatApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideAppContext(): Context = appContext

    @Singleton
    @Provides
    fun provideCatImagesRepository(
        catApi: CatApi,
        networkErrorHandler: NetworkErrorHandler
    ): CatImagesRepository =
        CatImagesRepository(catApi, networkErrorHandler)
}