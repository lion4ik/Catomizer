package com.github.catomizer.di

import android.app.DownloadManager
import android.content.Context
import com.github.catomizer.catgallery.repository.CatImagesRepository
import com.github.catomizer.data.DownloadHelper
import com.github.catomizer.data.db.FavoriteCatsDao
import com.github.catomizer.data.network.CatApi
import com.github.catomizer.error.ErrorHandler
import com.github.catomizer.error.NetworkErrorMapper
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
        favoriteCatsDao: FavoriteCatsDao,
        networkErrorHandler: NetworkErrorMapper
    ): CatImagesRepository =
        CatImagesRepository(catApi, favoriteCatsDao, networkErrorHandler)

    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandler = ErrorHandler()

    @Singleton
    @Provides
    fun provideDownloadManager(): DownloadManager =
        appContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    @Singleton
    @Provides
    fun provideDownloadHelper(downloadManager: DownloadManager): DownloadHelper =
        DownloadHelper(downloadManager)
}