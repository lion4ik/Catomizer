package com.github.catomizer.di

import android.content.Context
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.error.ErrorHandler
import com.github.catomizer.error.NetworkErrorMapper
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
        networkErrorHandler: NetworkErrorMapper
    ): CatImagesRepository =
        CatImagesRepository(catApi, networkErrorHandler)

    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandler = ErrorHandler()
}